package am.neovision.admin.toolkit.controller;

import am.neovision.admin.toolkit.dto.SectionInfo;
import am.neovision.admin.toolkit.dto.search.Term;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hakob.hakobyan created on 12/1/2020
 */
@Controller
@RequestMapping("search")
public class SearchController extends AbstractController {

    @Override
    public SectionInfo section() {
        section.setTitle("Dashboard");
        section.setDescription("Dashboard");
        return section;
    }

    @PostMapping()
    public String search(Term term, Model model) {

        model.addAttribute("term", term.getValue());

        return "search/result";
    }
}
