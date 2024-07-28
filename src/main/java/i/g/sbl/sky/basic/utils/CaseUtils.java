package i.g.sbl.sky.basic.utils;

public class CaseUtils {

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
