package am.neovision.domain.repository;

import am.neovision.domain.AbstractRepository;
import am.neovision.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends AbstractRepository<Transaction, Long> {

    Optional<Transaction> findByUuid(String uuid);

    @Query("select t from Transaction t where t.serialNumber like %?1 ")
    Page<Transaction> findBySearch(String search, Pageable pageable);

    @Query("select t from Transaction t where (t.fromAccount.uuid = ?1 or t.toAccount.uuid = ?1) and t.serialNumber like %?1 ")
    Page<Transaction> findByOwnerSearch(String ownerUuid, String search, Pageable pageable);

    @Query("select t from Transaction t where t.fromAccount.uuid = ?1 and t.serialNumber like %?2 ")
    Page<Transaction> findByFrom(String fromUuid, String search, Pageable pageable);

    @Query("select t from Transaction t where t.toAccount.uuid = ?1 and t.serialNumber like %?2 ")
    Page<Transaction> findByTo(String fromUuid, String search, Pageable pageable);

    @Query("select t from Transaction t where (t.toAccount.uuid = ?1 or t.fromAccount.uuid = ?1) and t.serialNumber like %?2 ")
    Page<Transaction> findByBankAccount(String fromUuid, String search, Pageable pageable);
}
