package com.banquito.core.account.service;

import com.banquito.core.account.controller.dto.AccountTransactionDTO;
import com.banquito.core.account.model.Account;
import com.banquito.core.account.model.AccountTransaction;
import com.banquito.core.account.repository.AccountRepository;
import com.banquito.core.account.repository.AccountTransactionRepository;
import com.banquito.core.account.util.mapper.AccountTransactionMapper;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class AccountTransactionService {

    private final AccountTransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountTransactionMapper accountTransactionMapper;

    public AccountTransactionService(AccountTransactionRepository transactionRepository,
            AccountRepository accountRepository, AccountTransactionMapper accountTransactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountTransactionMapper = accountTransactionMapper;
    }

    public AccountTransaction processTransaction(AccountTransactionDTO dto) {
        if (Objects.nonNull(dto.getUniqueKey()) && Objects.nonNull(dto.getAccountId())
                && Objects.nonNull(dto.getCodeChannel()) && Objects.nonNull(dto.getAmmount())) {
            Account account = accountRepository.getById(dto.getAccountId());
            AccountTransaction transaction1 = this.accountTransactionMapper.toPersistence(dto);
            AccountTransaction transaction = new AccountTransaction();
            transaction1.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
            transaction1.setBookingDate(Timestamp.valueOf(LocalDateTime.now()));
            transaction1.setValueDate(Timestamp.valueOf(LocalDateTime.now()));
            transaction1.setApplyTax(false);
            updateAccountBalance(account,transaction1);
            return transactionRepository.save(transaction1);

        }
        throw new RuntimeException("Error en los datos de la transaccion");
    }

    private void updateAccountBalance(Account account, AccountTransaction transaction) {
        BigDecimal currentBalance = account.getCurrentBalance();
        BigDecimal aviableBalance = account.getAvailableBalance();
        BigDecimal transactionammount = transaction.getAmmount();

        if ("DEB".equals(transaction.getTransactionType())) {
            BigDecimal newCBalance = currentBalance.subtract(transactionammount);
            BigDecimal newABalance = aviableBalance.subtract(transactionammount);
            account.setCurrentBalance(newCBalance);
            account.setAvailableBalance(newABalance);;
        } else if ("CRE".equals(transaction.getTransactionType())) {
            BigDecimal newBalance = currentBalance.add(transactionammount);
            BigDecimal newABalance = aviableBalance.add(transactionammount);
            account.setCurrentBalance(newBalance);
            account.setAvailableBalance(newABalance);;
        }
        accountRepository.save(account);
    }

    public List<AccountTransaction> findTransactionsByCodeUniqueAccount(String codeUniqueAccount) {
        List<AccountTransaction> transactions = transactionRepository.findByAccount_CodeUniqueAccount(codeUniqueAccount);
        return transactions; 
    }
}
