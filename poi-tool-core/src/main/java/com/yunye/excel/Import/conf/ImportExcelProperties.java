package com.yunye.excel.Import.conf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.util.NotImplemented;

import java.util.List;

/**
 * 导入的配置
 * @author huangfu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImportExcelProperties {
    /**
     * 读取sheet的index  读取那几个sheet页面
     */
    List<Integer> sheets;
    /**
     * 开始行的索引
     */
    Integer rowStartIndex = 0;

    Boolean isAllSheet;

}
