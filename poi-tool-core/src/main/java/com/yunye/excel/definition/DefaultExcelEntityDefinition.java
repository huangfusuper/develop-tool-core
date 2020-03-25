package com.yunye.excel.definition;

import com.yunye.excel.definition.base.BaseExcelEntityDefinition;

/**
 * 默认的解析器
 * @author huangfu
 */
public class DefaultExcelEntityDefinition extends BaseExcelEntityDefinition {
    @Override
    public void parsingExcelEntity(Class entityClass) {
        setColumnHidden(true);
        getDefinePool().put("name",this);
    }

    public static void main(String[] args) {
        DefaultExcelEntityDefinition defaultExcelEntityDefinition = new DefaultExcelEntityDefinition();
        defaultExcelEntityDefinition.parsingExcelEntity(Object.class);
        BaseExcelEntityDefinition baseExcelEntityDefinition = (BaseExcelEntityDefinition)defaultExcelEntityDefinition.getDefinePool().get("name");
        System.out.println(baseExcelEntityDefinition.getColumnHidden());
    }
}
