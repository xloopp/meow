package net.TrxaXe.meow.client;

public class DecodeMeow {
    public static boolean Modified = false;
    public static String unmeow(String text) {
        StringBuilder result = new StringBuilder();
        Modified = false;
        int i = 0;
        if (text == null) {
            text = "";
        }
        int n = text.length();
        while (i < n) {
            char c = text.charAt(i);
            if (c == '呜') {
                i++;
                continue;
            }
            if (c != '喵' && c != '\u200C' && !(c == ' ' && i + 1 < n &&
                    (text.charAt(i+1) == '喵' || text.charAt(i+1) == ' ' || text.charAt(i+1) == '\u200C'))) {
                result.append(c);
                i++;
                continue;
            }
            if (c == ' ' && i > 0 && (text.charAt(i-1) != '喵' || text.charAt(i-1) != ' ' || text.charAt(i-1) != '\u200C')) {
                result.append(c);
                i++;
                continue;
            }
            StringBuilder meow = new StringBuilder();
            while (i < n && (c == '喵' || c == ' ' || c == '\u200C')) {
                meow.append(c);
                i++;
                if (i < n) {
                    c = text.charAt(i);
                }
            }
            String ternaryStr = meow.toString()
                    .replace(' ', '0')
                    .replace('喵', '1')
                    .replace('\u200C', '2');
            try {
                int charCode = Integer.parseInt(ternaryStr, 3);
                if ((char) charCode == '"' || (char) charCode == '\\') result.append('\\');
                result.append((char) charCode);
                Modified = true;
            } catch (NumberFormatException e) {
                result.append(meow);
            }
        }
        return result.toString();
    }
}
