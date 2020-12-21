/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.neovision.admin.toolkit.controller;

import am.neovision.admin.toolkit.dto.AccountDto;
import am.neovision.admin.toolkit.dto.SectionInfo;
import am.neovision.admin.toolkit.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author hakob
 */
@Controller
@RequestMapping(value = {"/", "/dashboard"})
public class DashboardController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String dashboard(Model model) {
        SectionInfo section = new SectionInfo();
        section.setDescription("Dashboard");
        model.addAttribute("section", section);
        String dashboard = "";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AccountDto user = accountService.findByEmail(auth.getName());
        if (user == null) {
            section.setTitle("Admin dashboard");
            model.addAttribute("accountsCount", accountService.getCount());
            dashboard = "admin";
        } else {
            section.setTitle("Dashboard");
            dashboard = "account";
        }
        return "dashboard/"+dashboard;
    }
}
