package am.neovision.config;

import am.neovision.service.LoginAttemptService;
import am.neovision.util.RequestUtils;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hakob.hakobyan created on 8/11/2020
 */
@Slf4j
@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        loginAttemptService.loginFailed(RequestUtils.getClientIP(request));

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.sendRedirect("/login?error=" + exception.getLocalizedMessage());
    }
}
