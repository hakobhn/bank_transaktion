/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.neovision.admin.toolkit.config;

import am.neovision.admin.toolkit.domain.repository.AccountRepository;
import am.neovision.admin.toolkit.dto.AccountInfo;
import am.neovision.admin.toolkit.dto.GenderEnum;
import am.neovision.admin.toolkit.service.LoginAttemptService;
import am.neovision.admin.toolkit.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * @author hakob
 */
@Slf4j
@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        loginAttemptService.loginSucceeded(RequestUtils.getClientIP(request));

        //set our response to OK status
        response.setStatus(HttpServletResponse.SC_OK);

        accountRepository.findByEmail(authentication.getName()).ifPresent(
                account -> {
                    HttpSession session = request.getSession();

                    String avatar = account.getAvatar();
                    if (StringUtils.isBlank(avatar)) {
                        avatar = Optional.ofNullable(account.getGender())
                                .map(gender -> {
                                    if (gender.equals(GenderEnum.FEMALE)) {
                                        return "/dist/img/avatar-female.jpg";
                                    } else {
                                        return "/dist/img/avatar-male.jpg";
                                    }
                                }).get();
                    }
                    session.setAttribute("account", AccountInfo.builder()
                            .fullName(account.getFirstName() + " " + account.getLastName())
                            .avatar(avatar).build());
                }
        );
        if (authentication.getAuthorities().isEmpty()) {
            response.sendRedirect("/");
        } else {
            Optional.ofNullable(request.getSession(false))
                    .ifPresent(session -> {
                        session.removeAttribute("CAPTCHA");
                        try {
                            response.sendRedirect(
                                    Optional.ofNullable(session.getAttribute("SPRING_SECURITY_SAVED_REQUEST"))
                                            .map(saved -> {
                                                String targetUrl =
                                                        ((DefaultSavedRequest) saved).getRequestURL();
                                                if (!targetUrl.equals("/login")) {
                                                    return ((DefaultSavedRequest) saved).getRequestURL();
                                                } else {
                                                    return "/";
                                                }
                                            }).orElse("/"));
                        } catch (IOException e) {
                            log.error("After login redirect error: {}", e.getLocalizedMessage());
                        }
                    });
        }
    }
}
