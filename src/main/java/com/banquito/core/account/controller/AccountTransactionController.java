package com.banquito.core.account.controller;

import com.banquito.core.account.controller.dto.AccountTransactionDTO;
import com.banquito.core.account.model.AccountTransaction;
import com.banquito.core.account.service.AccountTransactionService;
import com.banquito.core.account.util.mapper.AccountTransactionMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT })
@RestController
@RequestMapping("account-transactions") // quitar api
public class AccountTransactionController {

    private AccountTransactionService transactionService;
    private final AccountTransactionMapper transactionMapper;

    public AccountTransactionController(AccountTransactionService transactionService,
            AccountTransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @PostMapping
    public ResponseEntity<AccountTransaction> createTransaction(@RequestBody AccountTransactionDTO dto) {
        try {
            AccountTransaction dtoTr = this.transactionService.processTransaction(dto);
            return ResponseEntity.ok(dtoTr);
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<AccountTransactionDTO>> getTransactionsByCodeUniqueAccount(@RequestParam String codeUniqueAccount) {
        List<AccountTransaction> transactions = transactionService.findTransactionsByCodeUniqueAccount(codeUniqueAccount);
        List<AccountTransactionDTO> transactionDTOs = transactionMapper.toDTOList(transactions);
        return ResponseEntity.ok(transactionDTOs);
    }
}
