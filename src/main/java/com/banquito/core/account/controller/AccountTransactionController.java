package com.banquito.core.account.controller;

import com.banquito.core.account.controller.dto.AccountTransactionDTO;
import com.banquito.core.account.model.AccountTransaction;
import com.banquito.core.account.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT })
@RestController
@RequestMapping("/api/account-transactions") // quitar api
public class AccountTransactionController {

    @Autowired
    private AccountTransactionService transactionService;

    // Endpoint para crear una nueva transacción
    @PostMapping
    public ResponseEntity<AccountTransaction> createTransaction(@RequestBody AccountTransactionDTO dto) {
        try {
            AccountTransaction dtoTr = this.transactionService.processTransaction(dto);
            return ResponseEntity.ok(dtoTr);
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        /*
         * AccountTransaction transaction = transactionService.processTransaction(
         * accountId, codeChannel, uniqueKey, transactionType, transactionSubtype,
         * reference, ammount,
         * creditorBankCode, creditorAccount, debtorBankCode, debtorAccount,
         * bookingDate, valueDate,
         * applyTax, parentTransactionKey, state, notes);
         * 
         * return new ResponseEntity<>(transaction, HttpStatus.CREATED);
         */
    }

    // Otros endpoints según sea necesario (por ejemplo, obtener transacciones por
    // cuenta)
}
