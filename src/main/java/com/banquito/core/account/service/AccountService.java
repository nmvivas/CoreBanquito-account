package com.banquito.core.account.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.banquito.core.account.controller.dto.AccountDTO;
import com.banquito.core.account.model.Account;
import com.banquito.core.account.repository.AccountRepository;
import com.banquito.core.account.util.mapper.AccountMapper;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository repository, AccountMapper accountMapper) {
        this.repository = repository;
        this.accountMapper = accountMapper;
    }

    public Account obtainAccount(String codeUniqueAccount) {
        Optional<Account> accountOpt = this.repository.findByCodeUniqueAccount(codeUniqueAccount);
        if (accountOpt.isPresent()) {
            return accountOpt.get();
        } else {
            throw new RuntimeException("No existe la ceunta con el numero" + codeUniqueAccount);
        }
    }

    public AccountDTO create(AccountDTO dto) {
        //TODO: Generacion de codigo unico de cuenta
        //TODO: Generacion de codigo interno de cuenta
        //TODO: Generacion de codigo internacional de cunta 
        //TODO: Generacion de numero de cuenta 

        Optional <Account> code_unique = this.repository.findByCodeUniqueAccount(dto.getCodeUniqueAccount());
        if(code_unique!= null){
            throw new RuntimeException("codigo unico repetido");
        }
        Account account = this.accountMapper.toPersistence(dto);
        account.setCreationDate(LocalDateTime.now());
        account.setLastModifiedDate(LocalDateTime.now());
        account.setState("INA");
        Account accountCreated = this.repository.save(account);
        return this.accountMapper.toDTO(accountCreated);
    }

    public Account saveAccount(Account account) {
        return repository.save(account);
    }

}
