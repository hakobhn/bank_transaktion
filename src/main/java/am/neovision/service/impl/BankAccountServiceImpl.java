package am.neovision.service.impl;

import am.neovision.converter.BankAccountToDtoConverter;
import am.neovision.domain.AbstractRepository;
import am.neovision.domain.model.BankAccount;
import am.neovision.domain.repository.BankAccountRepository;
import am.neovision.dto.BankAccountAdd;
import am.neovision.dto.BankAccountDto;
import am.neovision.service.AbstractService;
import am.neovision.service.BankAccountService;
import am.neovision.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

/**
 * @author hakob.hakobyan created on 11/20/2020
 */
@Slf4j
@Service("bankAccountService")
public class BankAccountServiceImpl extends AbstractService<BankAccount, BankAccountDto> implements BankAccountService {

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

    public BankAccountDto add(BankAccountAdd bankAccountAdd) {
        BankAccount account = new BankAccount();
        BeanUtils.copyProperties(bankAccountAdd, account);
        account.setUuid(StringUtil.generateUUID());
        return save(account);
    }
}
