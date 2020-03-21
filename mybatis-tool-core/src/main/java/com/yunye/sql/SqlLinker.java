package com.yunye.sql;

import com.yunye.dto.Page;

/**
 * sql链接器
 * @author huangfu
 */
public class SqlLinker {
    /**
     * 执行格式化sql
     */
    public static void execute(Page<?> page,BaseSqlPageFormat baseSqlPageFormat,StringBuilder sqlLink){
        baseSqlPageFormat.formatSql(page,sqlLink);
    }
}
