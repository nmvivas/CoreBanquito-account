package com.banquito.core.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.account.model.Account;

public interface AccountRepository extends JpaRepository<Account,Integer>{
    Optional <Account> findByCodeUniqueAccount(String codeUniqueAcount);
    Optional <Account> findByClientId(Integer clientId);

}
