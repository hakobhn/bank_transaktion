package am.neovision.controller;

import am.neovision.dto.SectionInfo;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by hakob on 05/05/20.
 */

@Controller
public class CustomErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        SectionInfo section = new SectionInfo();
        section.setDescription("Page error");
        model.addAttribute("section", section);

        // get error status
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // TODO: log error details here
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            // display specific error page
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                section.setTitle("404 Page not found");
                return "errors/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                section.setTitle("500 Internal server error");
                return "errors/500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                section.setTitle("403 Forbidden");
                return "errors/403";
            }
        }

        // display generic error
        return "errors/500";
    }
}