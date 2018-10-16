package net.daw.helper;

public class EncodingHelper {

    public static String escapeQuotes(String str) {
        char[] chars = str.toCharArray();
        String strFinal = "";
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '"') {
                strFinal += String.valueOf("'");
            } else {
                strFinal += String.valueOf(chars[i]);
            }
        }
        return String.valueOf(strFinal);
    }

}
