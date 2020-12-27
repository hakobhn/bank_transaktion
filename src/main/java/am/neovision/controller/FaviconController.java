package am.neovision.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hakob.hakobyan created on 10/16/2020
 */
@Controller
class FaviconController {

    @GetMapping("favicon.ico")
    public void getFavicon(HttpServletResponse response) throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("/favicon.ico");
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(in, response.getOutputStream());
    }
}
