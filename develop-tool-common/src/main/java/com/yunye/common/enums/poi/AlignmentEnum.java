package com.yunye.common.enums.poi;

/**
 * 对齐方式
 * @author huangfu
 */
public enum AlignmentEnum {

     CENTER_JUSTIFY("居中")
    ,LEFT_JUSTIFY("居左")
    ,Align_right("居右")
    ;

    private String alignment;

    AlignmentEnum(String alignment) {
        this.alignment = alignment;
    }

    AlignmentEnum() {
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }
}
