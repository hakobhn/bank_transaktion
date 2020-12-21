/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.neovision.admin.toolkit.service.impl;

import am.neovision.admin.toolkit.converter.AccountToDtoConverter;
import am.neovision.admin.toolkit.domain.AbstractRepository;
import am.neovision.admin.toolkit.domain.model.Account;
import am.neovision.admin.toolkit.domain.repository.AccountRepository;
import am.neovision.admin.toolkit.dto.AccountAdd;
import am.neovision.admin.toolkit.dto.AccountDto;
import am.neovision.admin.toolkit.exceptions.NotFoundException;
import am.neovision.admin.toolkit.service.AbstractService;
import am.neovision.admin.toolkit.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author hakob
 */
@Service
public class AccountService extends AbstractService<Account, AccountDto> implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AccountToDtoConverter accountToDtoConverter;

    @Value("${root.email}")
    private String rootEmail;
    @Value("${root.password}")
    private String rootPassword;

    @Override
    protected AbstractRepository<Account, Long> getRepository() {
        return accountRepository;
    }

    @Override
    protected AccountToDtoConverter getToDtoConverter() {
        return accountToDtoConverter;
    }

    public AccountDto add(AccountAdd accountAdd) {
        if (!accountAdd.getPassword().equals(accountAdd.getRePassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (accountAdd.getEmail().equalsIgnoreCase(rootEmail)) {
            throw new RuntimeException("Provided email address is already being used");
        }
        Optional.ofNullable(findByEmail(accountAdd.getEmail()))
                .ifPresent(usr -> {
                            throw new RuntimeException("Provided email address is already being used");
                        }
                );
        Account user = new Account();
        BeanUtils.copyProperties(accountAdd, user);
        user.setUuid(StringUtil.generateUUID());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return save(user);
    }

    public AccountDto edit(AccountAdd accountAdd) {
        if (!accountAdd.getPassword().equals(accountAdd.getRePassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        if (accountAdd.getEmail().equalsIgnoreCase(rootEmail)) {
            throw new RuntimeException("Provided email address is already being used");
        }
        String uuid = Optional.ofNullable(findByEmail(accountAdd.getEmail()))
                .map(usr -> {
                            if (!usr.getUuid().equals(accountAdd.getUuid())) {
                                throw new RuntimeException("Provided email address is already being used");
                            }
                            return usr.getUuid();
                        }
                ).orElseThrow(NotFoundException::new);
        Account account = accountRepository.findByUuid(uuid).get();
        BeanUtils.copyProperties(accountAdd, account);
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        return save(account);
    }

    public AccountDto getCurrentAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return Optional.ofNullable(findByEmail(auth.getName())).orElseThrow(NotFoundException::new);
    }

    public AccountDto findByEmail(String email) {
        return accountToDtoConverter.convert(accountRepository.findByEmail(email).orElse(null));
    }

    public AccountDto findByUUID(String uuid) {
        return accountToDtoConverter.convert(accountRepository.findByUuid(uuid).orElseThrow(NotFoundException::new));
    }

    public void delete(String uuid) {
        Account account = accountRepository.findByUuid(uuid).orElseThrow(NotFoundException::new);
        delete(account.getId());
    }

    public Integer getCount() {
        return accountRepository.findAll().size();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails principal = accountRepository.findByEmail(email)
                .map(
                        account -> buildUserForAuthentication(account.getEmail(), account.getPassword(), List.of(new SimpleGrantedAuthority("USER")))
                )
                .orElseGet(
                    () -> {
                        if (email.equalsIgnoreCase(rootEmail)) {
                            return buildUserForAuthentication(rootEmail, rootPassword, List.of(new SimpleGrantedAuthority("ADMIN")));
                        } else {
                            throw new UsernameNotFoundException("Email not found");
                        }
                    }
                );

        return principal;

    }

    private UserDetails buildUserForAuthentication(String email, String password, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(email, bCryptPasswordEncoder.encode(password), authorities);
    }
}
