package am.neovision.service;


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

    Page<BankAccountDto> listForOwner(String ownerUuid, String search, Pageable pageable);
}
