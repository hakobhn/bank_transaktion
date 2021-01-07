package am.neovision.controller;

import am.neovision.converter.RequestToDatatableRequestConverter;
import am.neovision.dto.SectionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class AbstractController {

    @Autowired
    protected RequestToDatatableRequestConverter requestToDtReqConverter;

    protected SectionInfo section = new SectionInfo();

    @ModelAttribute("section")
    public abstract SectionInfo section();
}
