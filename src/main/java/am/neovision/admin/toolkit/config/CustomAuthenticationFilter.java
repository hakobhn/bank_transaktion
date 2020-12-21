package am.neovision.admin.toolkit.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * @author hakob.hakobyan created on 11/9/2020
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String rootEmail;
    private String rootPassword;

    CustomAuthenticationFilter(String rootEmail, String rootPassword) {
        this.rootEmail = rootEmail;
        this.rootPassword = rootPassword;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        HttpSession session = request.getSession(true);
        String captchaStr = (String) session.getAttribute("CAPTCHA");
        if (StringUtils.isNoneBlank(captchaStr)) {
            String[] curCaptcha = request.getParameterMap().get("captcha");
            if (curCaptcha != null && curCaptcha.length > 0 && !captchaStr.equalsIgnoreCase(curCaptcha[0])) {
                throw new BadCredentialsException("Invalid captcha");
            }
        }

        String email = request.getParameterMap().get("email")[0];
        String password = request.getParameterMap().get("password")[0];

        UsernamePasswordAuthenticationToken auth;
        try {
            if (rootEmail.equals(email) && rootPassword.equals(password)) {
                auth = new UsernamePasswordAuthenticationToken(email, password,
                        List.of(new SimpleGrantedAuthority("ADMIN")));
//                auth.setAuthenticated(true);
            } else {
                auth = new UsernamePasswordAuthenticationToken(email, password);
                this.getAuthenticationManager().authenticate(auth);
            }
        } catch (BadCredentialsException bce) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new UsernameNotFoundException("Invalid email or password");
        }

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        return auth;
    }

}
