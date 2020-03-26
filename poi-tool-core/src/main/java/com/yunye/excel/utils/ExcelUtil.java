package com.yunye.excel.utils;

import com.sun.istack.internal.Nullable;
import com.yunye.common.annotations.poi.excel.ExcelDateFormat;
import com.yunye.common.annotations.poi.excel.ExcelImportField;
import com.yunye.common.enums.poi.DataTypeEnum;
import com.yunye.common.exception.ExceptionModel;
import com.yunye.common.utils.RuleCheckUtil;
import com.yunye.excel.Import.conf.ImportExcelProperties;
import com.yunye.excel.definition.DefaultImportExcelEntityDefinition;
import com.yunye.excel.definition.base.ExcelEntityDefinition;
import com.yunye.excel.model.ClassEntityDefinition;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Excel的操作对象
 * @author huangfu
 */
public class ExcelUtil {

    private static final Map<String, ClassEntityDefinition> DEFINITION_POOL = new ConcurrentHashMap<>(8);
    private static final ImportExcelProperties DEFAULT_IMPORT_EXCEL_PROPERTIES = new ImportExcelProperties(null,0,true);
    /**
     * 解析Excel
     * @param excelEntityClass 对应的实体类对象
     * @param importExcelProperties 导入Excel的配置
     * @param importDefinition 导入定义类对象
     * @param exportDefinition 导出定义类对象
     * @param <T> 返回类型
     * @return 返回Excel数据
     */
    public static <T> List<T> importExcel(Class<T> excelEntityClass,
                                          @Nullable ImportExcelProperties importExcelProperties,
                                          @Nullable ExcelEntityDefinition importDefinition,
                                          @Nullable ExcelEntityDefinition exportDefinition){

        RuleCheckUtil.objectNotNull(excelEntityClass,new ExceptionModel("30000001","Excel映射实体类对象不能为null!"));
        if(importExcelProperties == null){
            importExcelProperties = DEFAULT_IMPORT_EXCEL_PROPERTIES;
        }
        if(importDefinition == null){
            importDefinition = new DefaultImportExcelEntityDefinition(excelEntityClass,importExcelProperties);
        }

        if(exportDefinition == null){
            exportDefinition = new DefaultImportExcelEntityDefinition(excelEntityClass,importExcelProperties);
        }
        String className = excelEntityClass.getName();

        if(!DEFINITION_POOL.containsKey(className)){
            setPool(className,importDefinition,exportDefinition);
        }


        return null;
    }

    /**
     * TODO exportDefinition还未编写
     * @param className 全限定名
     * @param importDefinition 导入的定义
     * @param exportDefinition 导出的定义
     */
    private static void setPool(String className,
                         @Nullable ExcelEntityDefinition importDefinition,
                         @Nullable ExcelEntityDefinition exportDefinition){

        Map<String, ExcelEntityDefinition> stringExcelEntityDefinitionMap = importDefinition.parsingImportExcelEntity();
        ClassEntityDefinition classEntityDef = ClassEntityDefinition.builder()
                .className(className)
                .importExcelEntityDefinition(stringExcelEntityDefinitionMap)
                .exportExcelEntityDefinition(null)
                .build();
        DEFINITION_POOL.put(className,classEntityDef);
    }


    public static void main(String[] args) {
        ExcelUtil.importExcel(User.class,null,null,null);
    }
}

class User{
    @ExcelImportField(columnIndex = 0)
    @ExcelDateFormat("yyyy-MM-dd HH:mm:ss")
    private String id;
    @ExcelImportField(columnIndex = 1,trimStr = true,replace = {"name:张三","age:15"},dataTypeEnum = DataTypeEnum.TEXT)
    private String name;
}
