package am.neovision.admin.toolkit.domain.repository;

import am.neovision.admin.toolkit.domain.AbstractRepository;
import am.neovision.admin.toolkit.domain.model.Account;
import am.neovision.admin.toolkit.domain.model.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BankAccountRepository extends AbstractRepository<BankAccount, Long> {

    Optional<BankAccount> findByUuid(String uuid);

    @Query("select b from BankAccount b where b.number like %?1 ")
    Page<BankAccount> findBySearch(String search, Pageable pageable);
}
