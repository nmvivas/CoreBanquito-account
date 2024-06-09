package com.banquito.core.account.service;

import com.banquito.core.account.model.Account;
import com.banquito.core.account.model.AccountTransaction;
import com.banquito.core.account.repository.AccountTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class AccountTransactionService {

    @Autowired
    private AccountTransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    // Método para procesar una nueva transacción
    public AccountTransaction processTransaction(Integer accountId, String codeChannel, String uniqueKey,
                                                  String transactionType, String transactionSubtype, String reference,
                                                  BigDecimal amount, String creditorBankCode, String creditorAccount,
                                                  String debtorBankCode, String debtorAccount, LocalDateTime bookingDate,
                                                  LocalDateTime valueDate, boolean applyTax, String parentTransactionKey,
                                                  String state, String notes) {
        // Crear la instancia de la transacción
        AccountTransaction transaction = new AccountTransaction();
        transaction.setAccount(new Account(accountId)); 
        transaction.setCodeChannel(codeChannel);
        transaction.setUniqueKey(uniqueKey);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionSubtype(transactionSubtype);
        transaction.setReference(reference);
        transaction.setAmount(amount);
        transaction.setCreditorBankCode(creditorBankCode);
        transaction.setCreditorAccount(creditorAccount);
        transaction.setDebitorBankCode(debtorBankCode);
        transaction.setDebitorAccount(debtorAccount);
        transaction.setCreationDate(Timestamp.valueOf(LocalDateTime.now())); 
        transaction.setBookingDate(Timestamp.valueOf(bookingDate)); 
        transaction.setValueDate(Timestamp.valueOf(valueDate)); 
        transaction.setApplyTax(applyTax);
        transaction.setParentTransactionKey(parentTransactionKey);
        transaction.setState(state);
        transaction.setNotes(notes);

        // Actualizar el saldo de la cuenta dependiendo del tipo de transacción (crédito o débito)
        updateAccountBalance(transaction);

        // Guardar la transacción en la base de datos
        return transactionRepository.save(transaction);
    }

    // Método privado para actualizar el saldo de la cuenta según el tipo de transacción
    private void updateAccountBalance(AccountTransaction transaction) {
        Account account = transaction.getAccount();
        BigDecimal currentBalance = account.getCurrentBalance();
        BigDecimal transactionAmount = transaction.getAmount();

        if ("DEB".equals(transaction.getTransactionType())) { // Transacción de débito
            BigDecimal newBalance = currentBalance.subtract(transactionAmount);
            account.setCurrentBalance(newBalance);
        } else if ("CRE".equals(transaction.getTransactionType())) { // Transacción de crédito
            BigDecimal newBalance = currentBalance.add(transactionAmount);
            account.setCurrentBalance(newBalance);
        }

        // Guardar la cuenta actualizada
        accountService.saveAccount(account);
    }
}
