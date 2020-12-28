package am.neovision.domain.repository;

import am.neovision.domain.AbstractRepository;
import am.neovision.domain.model.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BankAccountRepository extends AbstractRepository<BankAccount, Long> {

    Optional<BankAccount> findByUuid(String uuid);

    Optional<BankAccount> findByNumber(String number);

    @Query("select b from BankAccount b where b.number like %?1 ")
    Page<BankAccount> findBySearch(String search, Pageable pageable);
}
