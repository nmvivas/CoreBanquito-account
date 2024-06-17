package com.banquito.core.account.controller;

import com.banquito.core.account.model.AccountTransaction;
import com.banquito.core.account.service.AccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("/api/account-transactions")//quitar api
public class AccountTransactionController {

    @Autowired
    private AccountTransactionService transactionService;

    // Endpoint para crear una nueva transacción
    @PostMapping
    public ResponseEntity<AccountTransaction> createTransaction(
            @RequestParam Integer accountId,
            @RequestParam String codeChannel,
            @RequestParam String uniqueKey,
            @RequestParam String transactionType,
            @RequestParam String transactionSubtype,
            @RequestParam String reference,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String creditorBankCode,
            @RequestParam(required = false) String creditorAccount,
            @RequestParam(required = false) String debtorBankCode,
            @RequestParam(required = false) String debtorAccount,
            @RequestParam LocalDateTime bookingDate,
            @RequestParam LocalDateTime valueDate,
            @RequestParam boolean applyTax,
            @RequestParam(required = false) String parentTransactionKey,
            @RequestParam String state,
            @RequestParam(required = false) String notes) {

        AccountTransaction transaction = transactionService.processTransaction(
                accountId, codeChannel, uniqueKey, transactionType, transactionSubtype, reference, amount,
                creditorBankCode, creditorAccount, debtorBankCode, debtorAccount, bookingDate, valueDate,
                applyTax, parentTransactionKey, state, notes);

        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    // Otros endpoints según sea necesario (por ejemplo, obtener transacciones por cuenta)
}
