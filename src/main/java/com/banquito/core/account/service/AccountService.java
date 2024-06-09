package com.banquito.core.account.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.banquito.core.account.model.Account;
import com.banquito.core.account.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public Account obtainAccount(String codeUniqueAccount) {
        Optional<Account> accountOpt = this.repository.findByCodeUniqueAccount(codeUniqueAccount);
        if (accountOpt.isPresent()) {
            return accountOpt.get();
        } else {
            throw new RuntimeException("No existe la ceunta con el numero" + codeUniqueAccount);
        }
    }

    public Account saveAccount(Account account) {
        return repository.save(account);
    }

}
