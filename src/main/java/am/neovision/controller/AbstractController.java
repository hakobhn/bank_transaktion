package am.neovision.controller;

import am.neovision.dto.SectionInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractController {
    protected SectionInfo section = new SectionInfo();

    @ModelAttribute("section")
    public abstract SectionInfo section();
}
