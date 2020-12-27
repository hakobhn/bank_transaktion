package am.neovision.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author hakob.hakobyan created on 11/9/2020
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        HttpSession session = request.getSession();
        String captchaStr = (String) session.getAttribute("CAPTCHA");
        if (StringUtils.isNoneBlank(captchaStr)) {
            String[] curCaptcha = request.getParameterMap().get("captcha");
            if (curCaptcha != null && curCaptcha.length > 0 && !captchaStr.equalsIgnoreCase(curCaptcha[0])) {
                throw new BadCredentialsException("Invalid captcha");
            }
        }
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(request.getParameterMap().get("email")[0],
                request.getParameterMap().get("password")[0], List.of(new SimpleGrantedAuthority("ADMIN")));

        try {
            return this.getAuthenticationManager().authenticate(token);
        } catch (BadCredentialsException bce) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new UsernameNotFoundException("Invalid email or password");
        }
    }

}