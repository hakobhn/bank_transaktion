package am.neovision.service.impl;

import am.neovision.converter.BankAccountToDtoConverter;
import am.neovision.domain.AbstractRepository;
import am.neovision.domain.model.BankAccount;
import am.neovision.domain.repository.AccountRepository;
import am.neovision.domain.repository.BankAccountRepository;
import am.neovision.dto.BankAccountAdd;
import am.neovision.dto.BankAccountDto;
import am.neovision.dto.TransactionDto;
import am.neovision.exceptions.NotFoundException;
import am.neovision.service.AbstractService;
import am.neovision.service.BankAccountService;
import am.neovision.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author hakob.hakobyan created on 11/20/2020
 */
@Slf4j
@Service("bankAccountService")
public class BankAccountServiceImpl extends AbstractService<BankAccount, BankAccountDto> implements BankAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankAccountToDtoConverter bankAccountToDtoConverter;

    @Override
    protected AbstractRepository getRepository() {
        return bankAccountRepository;
    }

    @Override
    protected Converter getToDtoConverter() {
        return bankAccountToDtoConverter;
    }

    @Override
    public Optional<BankAccountDto> findByNumber(String number) {
        return bankAccountRepository.findByNumber(number)
                .map(bankAccountToDtoConverter::convert);
    }

    public BankAccountDto add(BankAccountAdd bankAccountAdd) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setOwner(accountRepository.findByUuid(bankAccountAdd.getOwnerUuid()).orElseThrow(NotFoundException::new));
        bankAccount.setCurrency(bankAccountAdd.getCurrency());
        bankAccount.setNumber(bankAccountAdd.getNumber().replaceAll("[^0-9]", ""));
        bankAccount.setAmount(Float.parseFloat(bankAccountAdd.getAmount().replaceAll("[^a-zA-Z0-9\\s.]", "")));
        bankAccount.setUuid(StringUtil.generateUUID());
        return save(bankAccount);
    }

    public BankAccountDto edit(BankAccountAdd bankAccountAdd) {
        BankAccount bankAccount = bankAccountRepository.findByUuid(bankAccountAdd.getUuid()).orElseThrow(NotFoundException::new);
        BeanUtils.copyProperties(bankAccountAdd, bankAccount);
        return save(bankAccount);
    }

    public void delete(String uuid) {
        BankAccount bankAccount = bankAccountRepository.findByUuid(uuid).orElseThrow(NotFoundException::new);
        delete(bankAccount.getId());
    }

    @Override
    public Page<BankAccountDto> listForOwner(String ownerUuid, String search, Pageable pageable) {
        return bankAccountRepository.findByOwner(ownerUuid, search, pageable)
                .map(bankAccountToDtoConverter::convert);
    }
}
