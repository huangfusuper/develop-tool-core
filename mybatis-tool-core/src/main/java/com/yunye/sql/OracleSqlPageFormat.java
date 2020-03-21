package com.yunye.sql;

import com.yunye.dto.Page;

/**
 * oracle 分页格式化器
 * @author huangfu
 */
public class OracleSqlPageFormat extends BaseSqlPageFormat {
    @Override
    public void formatSql(Page<?> page, StringBuilder sqlLink) {
        if(page != null){
            int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
            sqlLink.insert(0, "select oraclePageFormat.*,rownum r from (")
                    .append(") oraclePageFormat where rownum < ")
                    .append(offset + page.getPageSize());
            sqlLink.insert(0, "select * from (")
                    .append(") where r >= ")
                    .append(offset);
        }
    }
}
