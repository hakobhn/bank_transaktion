/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.neovision.admin.toolkit.domain.repository;

import am.neovision.admin.toolkit.domain.AbstractRepository;
import am.neovision.admin.toolkit.domain.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author hakob
 */
@Repository
public interface AccountRepository extends AbstractRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUuid(String uuid);

    @Query("select a from Account a where a.email like %?1 or a.firstName like %?1 or a.lastName like %?1 ")
    Page<Account> findBySearch(String search, Pageable pageable);
}
