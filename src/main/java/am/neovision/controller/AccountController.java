package am.neovision.controller;

import am.neovision.converter.RequestToDatatableRequestConverter;
import am.neovision.exceptions.PermissionDenied;
import am.neovision.payload.ApiResponse;
import am.neovision.service.impl.AccountService;
import am.neovision.validator.AccountValidator;
import am.neovision.dto.*;
import groovy.util.logging.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("accounts")
public class AccountController extends AbstractController {

    @Autowired
    private RequestToDatatableRequestConverter requestToDtReqConverter;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public SectionInfo section() {
        section.setTitle("Accounts");
        section.setAdmin(accountService.isAdmin());
        return section;
    }

    @GetMapping
    public String list(Model model) {
        section.setDescription("Accounts list");

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
        section.setDescription("Add new account");

        model.addAttribute("action", "Add");
        model.addAttribute("currencies", Currency.values());

        if (!model.containsAttribute("account")) {
            model.addAttribute("account", new AccountAdd());
        }
        return "accounts/form";
    }

    @GetMapping("/edit/{uuid}")
    public String edit(@PathVariable String uuid, Model model) {
        section.setDescription("Edit account");

        model.addAttribute("currencies", Currency.values());

        AccountDto accountInfo = accountService.findByUUID(uuid);
        if (accountInfo.getAvatar().contains("/dist/img/avatar")) {
            accountInfo.setAvatar("");
        }
        AccountAdd accountAdd = new AccountAdd();
        BeanUtils.copyProperties(accountInfo, accountAdd);
        accountAdd.setPassword("");
        accountAdd.setRePassword("");

        model.addAttribute("account", accountAdd);
        return "accounts/form";
    }

    @PostMapping(value = "/save")
    public String save( @ModelAttribute("user") @Validated AccountAdd account,
                       final BindingResult bindingResult, final RedirectAttributes ra) {

        accountValidator.validate(account, bindingResult);
        if (StringUtils.isNoneBlank(account.getUuid())) {
            accountService.edit(account);
            ra.addFlashAttribute("successFlash", "User updated successfully.");
        } else {
            accountService.add(account);
            ra.addFlashAttribute("successFlash", "User added successfully.");
        }
        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.account", bindingResult);
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
        section.setTitle("Profile");
        section.setDescription("User Profile");

        Optional.ofNullable(accountService.getCurrentAccount()).ifPresent(
        acc -> {
            if (!acc.getUuid().equals(uuid)) {
                throw new PermissionDenied("Not allowed to get data.");
            }
        });

        AccountDto accountDto = accountService.findByUUID(uuid);

        Profile profile = new Profile();
        profile.setFullName(accountDto.getFirstName() + " " + accountDto.getLastName());
        profile.setEmail(accountDto.getEmail());
        profile.setCurrency(accountDto.getCurrency());

        if (StringUtils.isNoneBlank(accountDto.getAvatar())) {
            profile.setAvatar(accountDto.getAvatar());
        } else {
            if (accountDto.getGender() != null && accountDto.getGender().equals(Gender.FEMALE)) {
                profile.setAvatar("/dist/img/avatar-female.jpg");
            } else {
                profile.setAvatar("/dist/img/avatar-male.jpg");
            }
        }

        model.addAttribute("profile", profile);

        return "accounts/profile";
    }

    @RequestMapping(value = "/accounts/{uuid}", method = RequestMethod.GET)
    public String bankAccounts(@PathVariable String uuid, Model model) {
        section.setTitle("Profile");
        section.setDescription("User Profile");

        Optional.ofNullable(accountService.getCurrentAccount()).ifPresent(
                acc -> {
                    if (!acc.getUuid().equals(uuid)) {
                        throw new PermissionDenied("Not allowed to get data.");
                    }
                });

        AccountDto accountDto = accountService.findByUUID(uuid);

        Profile profile = new Profile();
        profile.setFullName(accountDto.getFirstName() + " " + accountDto.getLastName());
        profile.setEmail(accountDto.getEmail());
        profile.setCurrency(accountDto.getCurrency());

        if (StringUtils.isNoneBlank(accountDto.getAvatar())) {
            profile.setAvatar(accountDto.getAvatar());
        } else {
            if (accountDto.getGender() != null && accountDto.getGender().equals(Gender.FEMALE)) {
                profile.setAvatar("/dist/img/avatar-female.jpg");
            } else {
                profile.setAvatar("/dist/img/avatar-male.jpg");
            }
        }

        model.addAttribute("profile", profile);

        return "accounts/profile";
    }
}
