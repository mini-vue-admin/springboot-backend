package i.g.sbl.sky.basic.utils;

public class TruncateUtils {

    public static String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        } else {
            return truncate(str.substring(0, maxLength), maxLength);
        }
    }
}
