package am.neovision.service.impl;

import am.neovision.converter.TransactionToDtoConverter;
import am.neovision.domain.AbstractRepository;
import am.neovision.domain.model.BankAccount;
import am.neovision.domain.model.Transaction;
import am.neovision.domain.repository.BankAccountRepository;
import am.neovision.domain.repository.TransactionRepository;
import am.neovision.dto.Currency;
import am.neovision.dto.transaction.Processing;
import am.neovision.dto.transaction.TransactionAdd;
import am.neovision.dto.transaction.TransactionDto;
import am.neovision.dto.transaction.TransactionStatus;
import am.neovision.exceptions.NotFoundException;
import am.neovision.exceptions.PermissionDenied;
import am.neovision.service.AbstractService;
import am.neovision.service.ExchangeRatesService;
import am.neovision.service.TransactionService;
import am.neovision.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author hakob.hakobyan created on 11/20/2020
 */
@Slf4j
@Service("transactionService")
public class TransactionServiceImpl extends AbstractService<Transaction, TransactionDto> implements TransactionService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @Autowired
    private TransactionToDtoConverter transactionToDtoConverter;

    @Override
    protected AbstractRepository getRepository() {
        return transactionRepository;
    }

    @Override
    protected Converter getToDtoConverter() {
        return transactionToDtoConverter;
    }

    @Override
    public Optional<TransactionDto> findByUuid(String uuid) {
        return transactionRepository.findByUuid(uuid)
                .map(transactionToDtoConverter::convert);
    }

    public TransactionDto add(TransactionAdd transactionAdd) {
        Transaction transaction = new Transaction();
        transaction.setUuid(StringUtil.generateUUID());
        transaction.setFromAccount(bankAccountRepository.findByNumber(transactionAdd.getFromNumber()).orElseThrow(NotFoundException::new));
        transaction.setToAccount(bankAccountRepository.findByNumber(transactionAdd.getToNumber().replaceAll("[^0-9]", "")).orElseThrow(NotFoundException::new));
        transaction.setType(transactionAdd.getType());
        transaction.setSerialNumber(StringUtil.generateRandomString(16));
        transaction.setStatus(TransactionStatus.PENDING);
        Float fromAmount = Float.parseFloat(transactionAdd.getAmount().replaceAll("[^a-zA-Z0-9\\s.]", ""));
        Currency fromCurrency = transaction.getFromAccount().getCurrency();
        Currency toCurrency = transaction.getToAccount().getCurrency();
        Float toAmount = exchangeRatesService.calculateExchangeAmount(fromAmount, fromCurrency, toCurrency);
        transaction.setFromAmount(fromAmount);
        transaction.setToAmount(toAmount);
        return save(transaction);
    }

    @Override
    public void update(TransactionDto transaction) {
        Transaction entity = transactionRepository.findByUuid(transaction.getUuid()).orElseThrow(NotFoundException::new);
        entity.setStatus(transaction.getStatus());
        update(transaction);
    }

    @Override
    @Transactional(readOnly = false)
    public TransactionDto processing(Processing processing) {
        Transaction transaction = transactionRepository.findByUuid(processing.getUuid()).orElseThrow(NotFoundException::new);
        TransactionStatus status = Optional.ofNullable(TransactionStatus.valueOf(processing.getStatus())).orElseThrow(NotFoundException::new);
        transaction.setStatus(status);
        BankAccount fromAccount = transaction.getFromAccount();
        BankAccount toAccount = transaction.getToAccount();
        if (status == TransactionStatus.ACCEPTED) {
            fromAccount.setAmount(fromAccount.getAmount() - transaction.getFromAmount());
            toAccount.setAmount(toAccount.getAmount() + transaction.getToAmount());
        }
        return save(transaction);
    }

    @Override
    @Transactional(readOnly = false)
    public TransactionDto cancel(String uuid) {
        Transaction transaction = transactionRepository.findByUuid(uuid).orElseThrow(NotFoundException::new);
        if (!transaction.getFromAccount().getOwner().getUuid().equals(accountService.getCurrentAccount().getUuid())) {
            throw new PermissionDenied("Not your transaction");
        }
        transaction.setStatus(TransactionStatus.CANCELED);
        BankAccount fromAccount = transaction.getFromAccount();
        fromAccount.setAmount(fromAccount.getAmount() + transaction.getFromAmount());
        return save(transaction);
    }

    public void delete(String uuid) {
        Transaction transaction = transactionRepository.findByUuid(uuid).orElseThrow(NotFoundException::new);
        delete(transaction.getId());
    }

    @Override
    public Page<TransactionDto> fromList(String fromUuid, String search, Pageable pageable) {
        return transactionRepository.findByFrom(fromUuid, search, pageable)
                .map(transactionToDtoConverter::convert);
    }

    @Override
    public Page<TransactionDto> toList(String toUuid, String search, Pageable pageable) {
        return transactionRepository.findByTo(toUuid, search, pageable)
                .map(transactionToDtoConverter::convert);
    }

    @Override
    public Page<TransactionDto> bankAccountTransactionsList(String bankAccountUuid, String search, Pageable pageable) {
        return transactionRepository.findByBankAccount(bankAccountUuid, search, pageable)
                .map(transactionToDtoConverter::convert);
    }
}
