package am.neovision.admin.toolkit.service.impl;

import am.neovision.admin.toolkit.converter.BankAccountToDtoConverter;
import am.neovision.admin.toolkit.domain.AbstractRepository;
import am.neovision.admin.toolkit.domain.model.BankAccount;
import am.neovision.admin.toolkit.domain.repository.BankAccountRepository;
import am.neovision.admin.toolkit.dto.BankAccountAdd;
import am.neovision.admin.toolkit.dto.BankAccountDto;
import am.neovision.admin.toolkit.service.AbstractService;
import am.neovision.admin.toolkit.service.BankAccountService;
import am.neovision.admin.toolkit.util.StringUtil;
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
