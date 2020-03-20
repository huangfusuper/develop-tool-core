package com.yunye.common.enums.mybatis;

/**
 * @author huangfu
 */
public enum  JdbcTypeEnum {
     CHAR("String")
    ,VARCHAR("String")
    ,LONG_VARCHAR("String")
    ,NUMERIC("java.math.BigDecimal")
    ,DECIMAL("java.math.BigDecimal")
    ,BOOLEAN("boolean")
    ,BIT("boolean")
    ,TINYINT("byte")
    ,SMALLINT("short")
    ,INTEGER("int")
    ,BIGINT("long")
    ,REAL("flow")
    ,DOUBLE("double")
    ,BINARY(" byte[]")
    ,VARBINARY("byte[] ")
    ,LONG_VARBINARY("byte[]")
    ,DATE(" java.sql.Date")
    ,TIME(" java.sql.Time")
    ,TIMESTAMP(" java.sql.Timestamp ")
    ,CLOB("Clob")
    ,BLOB("Blob")
    ,ARRAY("ARRAY")
    ,DATA_LINK("java.net.URL[color=red][/color]")
    ;
     private String javaType;

    JdbcTypeEnum(String javaType) {
        this.javaType = javaType;
    }
}
