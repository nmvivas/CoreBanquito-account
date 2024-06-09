package com.banquito.core.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.account.model.AccountTransaction;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction,Integer>{

    List<AccountTransaction> findByAccount_AccountId(Integer accountId);
}
