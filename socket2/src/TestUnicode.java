import java.io.UnsupportedEncodingException;

public class TestUnicode {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "你好";
        System.out.println(s+" --的unicode编码是："+gbEncoding(s));
        System.out.println(gbEncoding(s) + " --转换成中文是："+decodeUnicode(gbEncoding(s)));
//        System.out.println(s+" --的GBK编码是："+toGBK(s));
        //System.out.println(gbEncoding(s) + " --转换成中文是："+decodeUnicode("\\u7b80\\u4ecb"));
    }

    /*
     * 中文转unicode编码
     */
    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
          //  unicodeBytes = unicodeBytes + "\\u" + hexB;
              unicodeBytes = unicodeBytes + hexB;
        }
        return unicodeBytes;
    }
    /*
     * unicode编码转中文
     */
    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.length();//字符串末尾
            String charStr = dataStr.substring(start, end);
//            if (end == -1) {//如果没有
//                charStr = dataStr.substring(start + 2, dataStr.length());
//            } else {//如果有
//                charStr = dataStr.substring(start + 2, end);
//            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = -2;
        }
        return buffer.toString();
    }
//    public static String toGBK(String source) throws UnsupportedEncodingException {
//        StringBuilder sb = new StringBuilder();
//        byte[] bytes = source.getBytes("GBK");
//        for(byte b : bytes) {
//            sb.append(" " + Integer.toHexString((b & 0xff)).toUpperCase());        }
//        return sb.toString();
//    }




}
