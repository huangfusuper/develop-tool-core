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

    /**
     * 下划线转换成驼峰命名法
     * @param underlineString 下划线格式的字符串
     * @return 驼峰标准格式数据
     */
    public static String underlineToHump(String underlineString) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < underlineString.length(); i++) {
            char c = underlineString.charAt(i);
            if (c == '_') {
                if (sb.length() > 0) {
                    nextUpperCase = true;
                }
            } else {
                if (nextUpperCase) {
                    sb.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String q = underlineToHump("_user_name");
        System.out.println(q);
    }
}
