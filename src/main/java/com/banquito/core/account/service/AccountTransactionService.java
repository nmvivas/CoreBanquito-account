package com.banquito.core.account.service;

import com.banquito.core.account.controller.dto.AccountTransactionDTO;
import com.banquito.core.account.model.Account;
import com.banquito.core.account.model.AccountTransaction;
import com.banquito.core.account.repository.AccountRepository;
import com.banquito.core.account.repository.AccountTransactionRepository;
import com.banquito.core.account.util.mapper.AccountTransactionMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class AccountTransactionService {

    private final AccountTransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountTransactionMapper accountTransactionMapper;

    // Método para procesar una nueva transacción
    /*
     * public AccountTransaction processTransaction(Integer accountId, String
     * codeChannel, String uniqueKey,
     * String transactionType, String transactionSubtype, String reference,
     * BigDecimal ammount, String creditorBankCode, String creditorAccount,
     * String debtorBankCode, String debtorAccount, LocalDateTime bookingDate,
     * LocalDateTime valueDate, boolean applyTax, String parentTransactionKey,
     * String state, String notes) {
     * // Crear la instancia de la transacción
     * AccountTransaction transaction = new AccountTransaction();
     * transaction.setAccount(new Account(accountId));
     * transaction.setCodeChannel(codeChannel);
     * transaction.setUniqueKey(uniqueKey);//ESTE DEBE SER GENERADO ya sea por back
     * o front y unico
     * transaction.setTransactionType(transactionType);
     * transaction.setTransactionSubtype(transactionSubtype);
     * transaction.setReference(reference);
     * transaction.setammount(ammount);
     * transaction.setCreditorBankCode(creditorBankCode);
     * transaction.setCreditorAccount(creditorAccount);
     * transaction.setDebitorBankCode(debtorBankCode);
     * transaction.setDebitorAccount(debtorAccount);
     * transaction.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
     * transaction.setBookingDate(Timestamp.valueOf(bookingDate)); //ESTO LO DEBES
     * MANDAR TU AL CREAR
     * transaction.setValueDate(Timestamp.valueOf(valueDate)); //ESTO LO DEBES
     * MANDAR TU IGUAL
     * transaction.setApplyTax(applyTax);
     * transaction.setParentTransactionKey(parentTransactionKey);
     * transaction.setState(state);//esto yo te mando el executed y tu controlas el
     * estado en back
     * transaction.setNotes(notes);
     * 
     * // Actualizar el saldo de la cuenta dependiendo del tipo de transacción
     * (crédito o débito)
     * updateAccountBalance(transaction);
     * 
     * // Guardar la transacción en la base de datos
     * return transactionRepository.save(transaction);
     * }
     */

    // Método para procesar una nueva transacción
    public AccountTransactionService(AccountTransactionRepository transactionRepository,
            AccountRepository accountRepository, AccountTransactionMapper accountTransactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountTransactionMapper = accountTransactionMapper;
    }

    public AccountTransaction processTransaction(AccountTransactionDTO dto) {
        // Crear la instancia de la transacción
        //System.out.println(dto.getUniqueKey().toString()+" "+dto.getAccountId()+" "+dto.getCodeChannel()+" "+dto.getAmmount().toString());
        if (Objects.nonNull(dto.getUniqueKey()) && Objects.nonNull(dto.getAccountId())
                && Objects.nonNull(dto.getCodeChannel()) && Objects.nonNull(dto.getAmmount())) {
            Account account = accountRepository.getById(dto.getAccountId());
            AccountTransaction transaction1 = this.accountTransactionMapper.toPersistence(dto);
            AccountTransaction transaction = new AccountTransaction();
            //transaction1.setAccount(account);
            transaction1.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
            transaction1.setBookingDate(Timestamp.valueOf(LocalDateTime.now())); // ESTO LO DEBES MANDAR TU AL CREAR
            transaction1.setValueDate(Timestamp.valueOf(LocalDateTime.now())); // ESTO LO DEBES MANDAR TU IGUAL
            transaction1.setApplyTax(false);
            updateAccountBalance(account,transaction1);
            return transactionRepository.save(transaction1);
/*
            //transaction.setId(null);
            transaction.setAccount(account);
            transaction.setAccountId(account.getId());
            transaction.setCodeChannel(dto.getCodeChannel());
            transaction.setUniqueKey(dto.getUniqueKey());// ESTE DEBE SER GENERADO ya sea por back o front y unico
            transaction.setTransactionType(dto.getTransactionType());
            transaction.setTransactionSubtype(dto.getTransactionSubtype());
            transaction.setReference(dto.getReference());
            transaction.setAmmount(dto.getAmmount());
            //transaction.setCreditorBankCode(dto.getcre);
            transaction.setCreditorAccount(dto.getCreditorAccount());
            //transaction.setDebitorBankCode(debtorBankCode);
            transaction.setDebitorAccount(dto.getDebitorAccount());
            transaction.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
            transaction.setBookingDate(Timestamp.valueOf(LocalDateTime.now())); // ESTO LO DEBES MANDAR TU AL CREAR
            transaction.setValueDate(Timestamp.valueOf(LocalDateTime.now())); // ESTO LO DEBES MANDAR TU IGUAL
            transaction.setApplyTax(false);
            //transaction.setParentTransactionKey(parentTransactionKey);
            transaction.setState(dto.getState());// esto yo te mando el executed y tu controlas el estado en back
            //transaction.setNotes(dto.getn);
            updateAccountBalance(transaction);
            return transactionRepository.save(transaction);*/

        }
        throw new RuntimeException("Error en los datos de la transaccion");
    }

    // Método privado para actualizar el saldo de la cuenta según el tipo de
    // transacción
    private void updateAccountBalance(Account account, AccountTransaction transaction) {
        //Account account = transaction.getAccount();
        BigDecimal currentBalance = account.getCurrentBalance();
        BigDecimal aviableBalance = account.getAvailableBalance();
        BigDecimal transactionammount = transaction.getAmmount();

        if ("DEB".equals(transaction.getTransactionType())) { // Transacción de débito la comparacion debe ser alrevez
                                                              // no con el "DEB"
            BigDecimal newCBalance = currentBalance.subtract(transactionammount);
            BigDecimal newABalance = aviableBalance.subtract(transactionammount);
            account.setCurrentBalance(newCBalance);
            account.setAvailableBalance(newABalance);;
        } else if ("CRE".equals(transaction.getTransactionType())) { // Transacción de crédito
            BigDecimal newBalance = currentBalance.add(transactionammount);
            BigDecimal newABalance = aviableBalance.add(transactionammount);
            account.setCurrentBalance(newBalance);
            account.setAvailableBalance(newABalance);;
        }

        // Guardar la cuenta actualizada
        accountRepository.save(account);
    }
}
