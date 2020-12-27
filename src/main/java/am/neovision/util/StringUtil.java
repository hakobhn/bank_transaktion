package am.neovision.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class StringUtil {

    private final static String SALT_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    public static String getTimeAndDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date();
        String timeAndDate = dateFormat.format(date);
        return timeAndDate;
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String generateRandomString(int length) {
        StringBuffer strBuffer = new StringBuffer();
        java.util.Random rnd = new java.util.Random();

        // build a random captchaLength chars salt
        while (strBuffer.length() < length) {
            int index = (int) (rnd.nextFloat() * SALT_CHARS.length());
            strBuffer.append(SALT_CHARS.charAt(index));
        }

        return strBuffer.toString();
    }
}
