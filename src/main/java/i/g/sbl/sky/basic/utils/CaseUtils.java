package i.g.sbl.sky.basic.utils;

public class CaseUtils {
    /**
     * 将字符串转换为蛇形命名法（snake_case）。
     * 例如："FirstName" 转换为 "first_name"。
     *
     * @param name 需要转换的字符串。
     * @return 转换后的蛇形命名法字符串。
     */
    public static String toSnakeCase(String name) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < name.length(); ++i) {
            char ch = name.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                char ch_ucase = (char) (ch + 32);
                if (i > 0) {
                    buf.append('_');
                }
                buf.append(ch_ucase);
            } else {
                buf.append(ch);
            }
        }
        return buf.toString();
    }

    /**
     * 将字符串转换为驼峰命名法（camelCase）。
     * 例如："first_name" 转换为 "firstName"。
     *
     * @param name 需要转换的字符串。
     * @return 转换后的驼峰命名法字符串。
     */
    public static String toCamelCase(String name) {
        StringBuilder buf = new StringBuilder();
        boolean capitalize = false;
        for (int i = 0; i < name.length(); ++i) {
            char ch = name.charAt(i);
            if (ch == '_') {
                capitalize = true;
            } else {
                if (capitalize) {
                    buf.append(Character.toUpperCase(ch));
                    capitalize = false;
                } else {
                    buf.append(ch);
                }
            }
        }
        return buf.toString();
    }

}
