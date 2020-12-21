/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.neovision.admin.toolkit.controller;

import am.neovision.admin.toolkit.service.LoginAttemptService;
import am.neovision.admin.toolkit.util.ImageUtils;
import am.neovision.admin.toolkit.util.RequestUtils;
import am.neovision.admin.toolkit.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.security.Principal;

/**
 * @author hakob
 */
@Slf4j
@Controller
public class AuthController {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView login(HttpServletRequest request, Principal user) {
        if (user != null) {
            ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        if (loginAttemptService.isBlocked(RequestUtils.getClientIP(request))) {
            modelAndView.addObject("captcha", true);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Max-Age", 0);
        response.setContentType("image/jpeg");

        String captchaStr = "";

        captchaStr = StringUtil.generateRandomString(6);
        try {
            HttpSession session = request.getSession(true);
            session.setAttribute("CAPTCHA", captchaStr);

            OutputStream outputStream = response.getOutputStream();

            ImageIO.write(ImageUtils.generateImageFromText(captchaStr), "png", outputStream);
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
