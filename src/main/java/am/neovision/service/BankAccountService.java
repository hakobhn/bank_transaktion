package am.neovision.service;


import am.neovision.domain.model.BankAccount;
import am.neovision.dto.AccountAdd;
import am.neovision.dto.AccountDto;
import am.neovision.dto.BankAccountAdd;
import am.neovision.dto.BankAccountDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BankAccountService {

    Optional<BankAccountDto> findByNumber(String number);

    BankAccountDto add(BankAccountAdd bankAccountAdd);

    BankAccountDto edit(BankAccountAdd accountAdd);

    void delete(String uuid);

    Page<BankAccountDto> list(String search, Pageable pageable);
}
