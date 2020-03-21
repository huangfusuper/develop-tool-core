package com.yunye.sql;

import com.yunye.dto.Page;

/**
 * mysql 的分页数据的格式化器
 * @author huangfu
 */
public class MySqlSqlPageFormat extends BaseSqlPageFormat {
    @Override
    public void formatSql(Page<?> page, StringBuilder sqlLink) {
        if(page != null){
            int offset = (page.getPageNo() - 1) * page.getPageSize();
            sqlLink.append(" limit ")
                    .append(offset)
                    .append(",")
                    .append(page.getPageSize());
        }
    }
}
