package am.neovision.service;


import am.neovision.dto.TransactionAdd;
import am.neovision.dto.TransactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TransactionService {

    Optional<TransactionDto> findByUuid(String uuid);

    TransactionDto add(TransactionAdd transactionAdd);

    void update(TransactionDto transaction);

    void delete(String uuid);

    void cancel(String uuid);

    Page<TransactionDto> list(String search, Pageable pageable);

    Page<TransactionDto> fromList(String bankAccountUuid, String search, Pageable pageable);

    Page<TransactionDto> toList(String bankAccountUuid, String search, Pageable pageable);

    Page<TransactionDto> bankAccountTransactionsList(String bankAccountUuid, String search, Pageable pageable);
}
