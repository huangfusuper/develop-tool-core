package com.yunye.sql;

import com.yunye.dto.Page;

/**
 * 处理分页数据的基类
 * @author huangfu
 */
public abstract class BaseSqlPageFormat {

    private BaseSqlPageFormat baseSqlPageFormat;

    /**
     * sql格式化器
     * @param page 分页数据
     * @param sqlLink sql链接器
     * @return 分页sql
     */
    public abstract void formatSql(Page<?> page,StringBuilder sqlLink);

    public BaseSqlPageFormat getBaseSqlPageFormat() {
        return baseSqlPageFormat;
    }

    public void setBaseSqlPageFormat(BaseSqlPageFormat baseSqlPageFormat) {
        this.baseSqlPageFormat = baseSqlPageFormat;
    }
}
