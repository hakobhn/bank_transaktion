package am.neovision.admin.toolkit.controller;

import am.neovision.admin.toolkit.dto.SectionInfo;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractController {
    protected SectionInfo section = new SectionInfo();

    @ModelAttribute("section")
    public abstract SectionInfo section();
}
