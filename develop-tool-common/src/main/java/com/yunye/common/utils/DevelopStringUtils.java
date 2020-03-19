package com.yunye.common.utils;

/**
 * 常用字符串处理工具
 * @author huangfu
 */
public class DevelopStringUtils {
    /**
     * 驼峰转换成下划线拼接的数据
     * @param camelCaseName
     * @return
     */
    public static String humpToUnderline(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String q = humpToUnderline("UserName").substring(0, humpToUnderline("UserName").length() - 1);
        System.out.println(q);
    }
}
