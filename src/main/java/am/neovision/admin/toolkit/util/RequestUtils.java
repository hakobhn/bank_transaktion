package am.neovision.admin.toolkit.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author hakob.hakobyan created on 11/9/2020
 */
public class RequestUtils {

    public static String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
