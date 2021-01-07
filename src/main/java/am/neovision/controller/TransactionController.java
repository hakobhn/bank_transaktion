package am.neovision.controller;

import am.neovision.dto.*;
import am.neovision.payload.ApiResponse;
import am.neovision.service.TransactionService;
import am.neovision.service.impl.AccountService;
import am.neovision.validator.BankAccountValidator;
import am.neovision.validator.TransactionValidator;
import groovy.util.logging.Slf4j;
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
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("transactions")
public class TransactionController extends AbstractController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionValidator transactionValidator;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public SectionInfo section() {
        section.setTitle("Transactions");
        section.setAdmin(accountService.isAdmin());
        return section;
    }

    @GetMapping
    public String list(Model model) {
        section.setDescription("Transactions list");

        if (accountService.isAdmin()) {
            return "transactions/admin_list";
        } else {
            return "transactions/account_list";
        }
    }

    @GetMapping(value = "/list_bank_account/{bankAccountUuid}")
    public String listForBankAccount(@PathVariable String bankAccountUuid, Model model) {
        section.setDescription("Transactions list");

        model.addAttribute("bankAccountUuid", bankAccountUuid);

        return "transactions/bank_account_list";
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> save(@ModelAttribute("transaction") @Validated TransactionAdd transaction,
                                  final BindingResult bindingResult) {

        transactionValidator.validate(transaction, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(new ApiResponse(false, "Failed to add bank account", bindingResult.getAllErrors().stream()
                    .map(Object::toString).collect(Collectors.joining("<br/>")), HttpStatus.BAD_REQUEST.value(), null),
                    HttpStatus.BAD_REQUEST);
        } else {
            transactionService.add(transaction);
            return new ResponseEntity(new ApiResponse(true, "Success", "Transaction added successfully", HttpStatus.OK.value(), null ), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list(HttpServletRequest req) {
        DatatableRequest dtReq = requestToDtReqConverter.convert(req);
        Page<TransactionDto> transactions = transactionService.list(dtReq.getSearch(), dtReq.getPageable());
        DataTable dataTable = new DataTable();

        dataTable.setData(transactions.getContent());
        dataTable.setRecordsTotal(transactions.getTotalElements());
        dataTable.setRecordsFiltered(transactions.getTotalElements());

        dataTable.setDraw(dtReq.getDraw());
        dataTable.setStart(dtReq.getStart());

        return ResponseEntity.ok(dataTable);
    }

    @RequestMapping(value = "/data_bank_account/{bankAccountUuid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listForBankAccount(@PathVariable String bankAccountUuid, HttpServletRequest req) {
        DatatableRequest dtReq = requestToDtReqConverter.convert(req);
        Page<TransactionDto> transactions = transactionService.bankAccountTransactionsList(bankAccountUuid, dtReq.getSearch(), dtReq.getPageable());
        DataTable dataTable = new DataTable();

        dataTable.setData(transactions.getContent());
        dataTable.setRecordsTotal(transactions.getTotalElements());
        dataTable.setRecordsFiltered(transactions.getTotalElements());

        dataTable.setDraw(dtReq.getDraw());
        dataTable.setStart(dtReq.getStart());

        return ResponseEntity.ok(dataTable);
    }

    @RequestMapping(value = "/data_from", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listFrom(HttpServletRequest req) {
        DatatableRequest dtReq = requestToDtReqConverter.convert(req);
        Page<TransactionDto> transactions = transactionService.fromList(accountService.getCurrentAccount().getUuid(),
                dtReq.getSearch(), dtReq.getPageable());
        DataTable dataTable = new DataTable();

        dataTable.setData(transactions.getContent());
        dataTable.setRecordsTotal(transactions.getTotalElements());
        dataTable.setRecordsFiltered(transactions.getTotalElements());

        dataTable.setDraw(dtReq.getDraw());
        dataTable.setStart(dtReq.getStart());

        return ResponseEntity.ok(dataTable);
    }

    @RequestMapping(value = "/data_to", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listTo(HttpServletRequest req) {
        DatatableRequest dtReq = requestToDtReqConverter.convert(req);
        Page<TransactionDto> transactions = transactionService.toList(accountService.getCurrentAccount().getUuid(),
                dtReq.getSearch(), dtReq.getPageable());
        DataTable dataTable = new DataTable();

        dataTable.setData(transactions.getContent());
        dataTable.setRecordsTotal(transactions.getTotalElements());
        dataTable.setRecordsFiltered(transactions.getTotalElements());

        dataTable.setDraw(dtReq.getDraw());
        dataTable.setStart(dtReq.getStart());

        return ResponseEntity.ok(dataTable);
    }

    @GetMapping("/manage/{uuid}")
    public String manage(@PathVariable String uuid, Model model) {
        section.setDescription("Manage transactin");

        model.addAttribute("statuses", TransactionStatus.values());

        model.addAttribute("transaction", transactionService.findByUuid(uuid));
        return "transactions/form";
    }

    @PostMapping(value = "/save")
    public String save(@ModelAttribute("transaction") @Validated TransactionDto transaction,
                       final BindingResult bindingResult, final RedirectAttributes ra) {

        if (bindingResult.hasErrors()) {
            ra.addFlashAttribute("org.springframework.validation.BindingResult.account", bindingResult);
            ra.addFlashAttribute("transction", transaction);
            return "transactions/form";
        } else {
            transactionService.update(transaction);
            ra.addFlashAttribute("successFlash", "User added successfully.");
            return "redirect:/transactions";
        }
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancel(@RequestParam String uuid) {
        transactionService.delete(uuid);
        return new ResponseEntity(new ApiResponse(true, "Success", "Transaction canceled successfully", HttpStatus.OK.value(), null ), HttpStatus.OK);
    }
}
