package am.neovision.controller;

import am.neovision.converter.RequestToDatatableRequestConverter;
import am.neovision.dto.BankAccountAdd;
import am.neovision.dto.BankAccountDto;
import am.neovision.dto.DataTable;
import am.neovision.dto.DatatableRequest;
import am.neovision.payload.ApiResponse;
import am.neovision.service.BankAccountService;
import am.neovision.service.impl.AccountService;
import am.neovision.validator.BankAccountValidator;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("bank_accounts")
public class BankAccountController {

    @Autowired
    private RequestToDatatableRequestConverter requestToDtReqConverter;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountValidator bankAccountValidator;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }


    @RequestMapping(value = "/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(HttpServletRequest req) {
        DatatableRequest dtReq = requestToDtReqConverter.convert(req);
        Page<BankAccountDto> bankAccounts = bankAccountService.list(dtReq.getSearch(), dtReq.getPageable());
        DataTable dataTable = new DataTable();

        dataTable.setData(bankAccounts.getContent());
        dataTable.setRecordsTotal(bankAccounts.getTotalElements());
        dataTable.setRecordsFiltered(bankAccounts.getTotalElements());

        dataTable.setDraw(dtReq.getDraw());
        dataTable.setStart(dtReq.getStart());

        return ResponseEntity.ok(dataTable);
    }

    @RequestMapping(value = "/data_owner/{ownerUuid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(@PathVariable String ownerUuid, HttpServletRequest req) {
        DatatableRequest dtReq = requestToDtReqConverter.convert(req);
        Page<BankAccountDto> bankAccounts = bankAccountService.listForOwner(ownerUuid, dtReq.getSearch(), dtReq.getPageable());
        DataTable dataTable = new DataTable();

        dataTable.setData(bankAccounts.getContent());
        dataTable.setRecordsTotal(bankAccounts.getTotalElements());
        dataTable.setRecordsFiltered(bankAccounts.getTotalElements());

        dataTable.setDraw(dtReq.getDraw());
        dataTable.setStart(dtReq.getStart());

        return ResponseEntity.ok(dataTable);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> save(@ModelAttribute("bankAccount") @Validated BankAccountAdd bankAccount,
                                  final BindingResult bindingResult) {

        bankAccountValidator.validate(bankAccount, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiResponse(false, "Failed to add bank account", bindingResult.getAllErrors().stream()
                    .map(Object::toString).collect(Collectors.joining("<br/>")), HttpStatus.BAD_REQUEST.value(), null),
                    HttpStatus.BAD_REQUEST);
        } else {
            bankAccountService.add(bankAccount);
            return new ResponseEntity(new ApiResponse(true, "Success", "Bank account added successfully", HttpStatus.OK.value(), null), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestParam String uuid) {
        bankAccountService.delete(uuid);
        return new ResponseEntity(new ApiResponse(true, "Success", "Bank account deleted successfully", HttpStatus.OK.value(), null), HttpStatus.OK);
    }
}
