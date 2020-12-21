package am.neovision.admin.toolkit.controller;

import am.neovision.admin.toolkit.converter.RequestToDatatableRequestConverter;
import am.neovision.admin.toolkit.dto.*;
import am.neovision.admin.toolkit.payload.ApiResponse;
import am.neovision.admin.toolkit.service.ProfileService;
import am.neovision.admin.toolkit.service.impl.AccountService;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("accounts")
public class AccountController {
    @Autowired
    private ProfileService profileService;

    @Autowired
    private RequestToDatatableRequestConverter requestToDtReqConverter;

    private AccountService accountService;
    @Autowired
    private List<String> currencies;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String list(Model model) {
        SectionInfo section = new SectionInfo();
        section.setTitle("Accounts");
        section.setDescription("Accounts list");
        model.addAttribute("section", section);

        return "accounts/list";
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(HttpServletRequest req) {
        DatatableRequest dtReq = requestToDtReqConverter.convert(req);
        Page<AccountDto> accounts = accountService.list(dtReq.getSearch(), dtReq.getPageable());
        DataTable dataTable = new DataTable();

        dataTable.setData(accounts.getContent());
        dataTable.setRecordsTotal(accounts.getTotalElements());
        dataTable.setRecordsFiltered(accounts.getTotalElements());

        dataTable.setDraw(dtReq.getDraw());
        dataTable.setStart(dtReq.getStart());

        return ResponseEntity.ok(dataTable);
    }

    @GetMapping("/add")
    public String add(Model model) {
        SectionInfo section = new SectionInfo();
        section.setTitle("Accounts");
        section.setDescription("Add new account");
        model.addAttribute("section", section);

        model.addAttribute("action", "Add");
        model.addAttribute("currencies", currencies);

        if (!model.containsAttribute("account")) {
            model.addAttribute("account", new AccountDto());
        }
        return "accounts/form";
    }

    @GetMapping("/edit/{uuid}")
    public String edit(@PathVariable String uuid, Model model) {
        SectionInfo section = new SectionInfo();
        section.setTitle("Accounts");
        section.setDescription("Edit account");
        model.addAttribute("section", section);

        model.addAttribute("currencies", currencies);

        AccountDto accountInfo = accountService.findByUUID(uuid);
        if (accountInfo.getAvatar().contains("/dist/img/avatar")) {
            accountInfo.setAvatar("");
        }

        model.addAttribute("account", accountInfo);
        return "accounts/form";
    }

    @PostMapping(value = "/save")
    public String save(@Valid @ModelAttribute("user") AccountAdd account, Model model,
                       final BindingResult bindingResult, final RedirectAttributes ra) {
        AccountDto accountEdit = null;
        if (StringUtils.isNoneBlank(account.getUuid())) {
            accountEdit = accountService.findByUUID(account.getUuid());
        }
        if (accountEdit != null) {
            accountService.edit(account);
            ra.addFlashAttribute("successFlash", "User updated successfully.");
        }
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            ra.addFlashAttribute("account", account);
            return "redirect:/accounts/add";
        } else {
            return "redirect:/accounts";
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestParam String uuid) {
//        try {
        accountService.delete(uuid);
//        } catch (Exception e) {
//            return new ResponseEntity(new ApiResponse(false, "Could not delete user. Please try again later!"),
//                    HttpStatus.BAD_REQUEST);
//        }
        return new ResponseEntity(new ApiResponse(true, "Success"), HttpStatus.OK);
    }

    @RequestMapping(value = "/profile/{uuid}", method = RequestMethod.GET)
    public String profile(@PathVariable String uuid, Model model) {
        SectionInfo section = new SectionInfo();
        section.setTitle("Profile");
        section.setDescription("User Profile");
        model.addAttribute("section", section);

        boolean ownProfile = true;
        AccountDto accountDto = accountService.getCurrentAccount();
        if (!uuid.equals(accountDto.getUuid())) {
            ownProfile = false;
        }

        Profile profile = new Profile();
        profile.setFullName(accountDto.getFirstName() + " " + accountDto.getLastName());
        profile.setEmail(accountDto.getEmail());

        if (StringUtils.isNoneBlank(accountDto.getAvatar())) {
            profile.setAvatar(accountDto.getAvatar());
        } else {
            if (accountDto.getGender() != null && accountDto.getGender().equals(GenderEnum.FEMALE)) {
                profile.setAvatar("/dist/img/avatar-female.jpg");
            } else {
                profile.setAvatar("/dist/img/avatar-male.jpg");
            }
        }

        model.addAttribute("ownProfile", ownProfile);
        model.addAttribute("profile", profile);

        return "accounts/profile";
    }

}
