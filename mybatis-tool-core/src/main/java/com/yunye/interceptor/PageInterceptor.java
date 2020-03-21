package com.yunye.interceptor;

import com.yunye.common.utils.ReflectUtils;
import com.yunye.dto.Page;
import com.yunye.help.SqlGenerateHelp;
import com.yunye.sql.MySqlSqlPageFormat;
import com.yunye.sql.OracleSqlPageFormat;
import com.yunye.sql.SqlLinker;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.*;
import java.util.List;

/**
 * 分页拦截器
 * @author huangfu
 */
@Intercepts(value = {@Signature(type = StatementHandler.class, method = "prepare",args = {Connection.class,Integer.class})})
public class PageInterceptor implements Interceptor {
    /**
     * 查询类型
     */
    private final String QUERY_TYPE = "findAll";
    private final String MY_SQL= "MYSQL";
    private final String ORACLE= "ORACLE";
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //路由语句处理程序
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        //语句处理程序
        StatementHandler delegate = (StatementHandler) ReflectUtils.getFieldValue(handler, "delegate");
        //获取mapper对象
        MappedStatement mappedStatement = (MappedStatement) ReflectUtils.getFieldValue(delegate, "mappedStatement");
        //获取方法签名
        String sqlId = mappedStatement.getId();
        if(sqlId.contains(QUERY_TYPE)){
            BoundSql boundSql = delegate.getBoundSql();
            //获取参数对象
            Object parameterObject = boundSql.getParameterObject();
            //获取参数映射
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            //获取sql代码
            String sql = boundSql.getSql();
            //初始化sql连接器
            StringBuilder sqlLink = new StringBuilder(sql);
            //获取数据库链接
            Connection connection = (Connection) invocation.getArgs()[0];
            //分页sql
            String formatSql = formatSql(parameterObject, sqlLink, connection, mappedStatement);
            ReflectUtils.setFieldValue(boundSql,"sql",formatSql);
        }

        return invocation.proceed();
    }

    /**
     * 格式化后的sql
     * @param paramObj 条件对象  用于拼接sql （查询总数的sql）
     * @param sqlLink 原本sql的链接器 这个是为了拼接分页sql
     * @param connection 术后句酷链接对象 这个是为了执行sql 总数sql
     * @param mappedStatement 映射的语句  关于数据库的配置全在这
     * @return 格式化后的sql 即分页sql
     */
    private String formatSql(Object paramObj,StringBuilder sqlLink,Connection connection, MappedStatement mappedStatement){

        if(paramObj instanceof SqlGenerateHelp){
            //获取条件对象
            SqlGenerateHelp sqlGenerateHelp = (SqlGenerateHelp)paramObj;
            //获取分页对象
            Page<?> page = sqlGenerateHelp.getPage();
            if(page != null){
                setTotalCount(sqlGenerateHelp,sqlLink.toString(),connection,mappedStatement);
                String dbType = getDbType(connection);
                if(dbType.toUpperCase().contains(MY_SQL)){
                    MySqlSqlPageFormat mySqlSqlPageFormat = new MySqlSqlPageFormat();
                    SqlLinker.execute(page,mySqlSqlPageFormat,sqlLink);
                } else if(dbType.toUpperCase().contains(ORACLE)){
                    OracleSqlPageFormat oracleSqlPageFormat = new OracleSqlPageFormat();
                    SqlLinker.execute(page,oracleSqlPageFormat,sqlLink);
                }
            }
        }

        return sqlLink.toString();
    }

    /**
     * 获取数据库信息
     * @param connection 数据库链接对象
     * @return 数据库类型
     */
    private static String getDbType(Connection connection){
        String dbType = null;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            dbType = metaData.getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbType;
    }

    /**
     * 这个其实就是一个sql执行器，去执行一个sql
     * 设置分页数据的总条数
     * @param sqlGenerateHelp sql条件帮助器
     * @param sql 拼装好但是没有分页的sql
     * @param connection 数据库链接对象
     * @param mappedStatement 映射的语句  关于数据库的配置全在这
     */
    private void setTotalCount(SqlGenerateHelp sqlGenerateHelp, String sql, Connection connection, MappedStatement mappedStatement){
        BoundSql boundSql = mappedStatement.getBoundSql(sqlGenerateHelp);
        String countSql = this.formatCountSql(sql);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(),countSql,parameterMappings,sqlGenerateHelp);
        //这个是遍历mybatis设置的mapping
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                //根据原来sql上的映射数据  来修改这个count的映射数据
                countBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        //参数处理程序设置{@code PreparedStatement}的参数。
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement,sqlGenerateHelp,countBoundSql);
        //开始设置jdbc原生的执行方法
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //设置准备执行的sql
            pstmt = connection.prepareStatement(countSql);
            //设置参数   向sql里面
            parameterHandler.setParameters(pstmt);
            //执行sql获取结果集
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int totalRecord = rs.getInt(1);
                //给当前的参数page对象设置总记录数
                sqlGenerateHelp.getPage().setTotalRecord(totalRecord);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if(pstmt != null){
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 返回一个格式化好的查询总数的sql
     * @param sql 原生没有分页的sql
     * @return 格式化好的查询总数的sql
     */
    private String formatCountSql(String sql){
        StringBuilder sb = new StringBuilder("SELECT COUNT(*) FROM ( ");
                sb.append(sql)
                .append(" ) pageCountTable");
        return sb.toString();
    }

}
