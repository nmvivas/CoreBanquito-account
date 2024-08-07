package com.banquito.core.account.controller;

import com.banquito.core.account.controller.dto.AccountDTO;
import com.banquito.core.account.model.Account;
import com.banquito.core.account.service.AccountService;
import com.banquito.core.account.util.mapper.AccountMapper;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT })
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountMapper accountMapper;
    private final AccountService service;

    public AccountController(AccountMapper accountMapper, AccountService service) {
        this.accountMapper = accountMapper;
        this.service = service;
    }

    @GetMapping("/by-unique-code/{codeUniqueAccount}")
    public ResponseEntity<AccountDTO> getAccountByCodeUniqueAccount(@PathVariable String codeUniqueAccount) {
        try {
            System.out.println("Va a buscar una cuenta por codigo unico:" + codeUniqueAccount);
            return ResponseEntity.ok(this.accountMapper.toDTO(this.service.obtainAccount(codeUniqueAccount)));
        } catch (RuntimeException rte) {
            return ResponseEntity.notFound().build();

        }
    }

    @GetMapping("/client-id/{clientId}")
    public ResponseEntity<AccountDTO> getAccountByClientId(@PathVariable Integer clientId) {
        try {
            System.out.println("Va a buscar una cuenta por id del cliente:" + clientId);
            return ResponseEntity.ok(this.accountMapper.toDTO(this.service.obtainAccountByClientId(clientId)));
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.notFound().build();

        }
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        try {
            List<AccountDTO> accounts = this.service.obtainAllAccounts();
            return ResponseEntity.ok(accounts);
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createOrUpdateAccount(@RequestBody AccountDTO dto) {
        try {
            AccountDTO dtoAC = this.service.create(dto);
            return new ResponseEntity<>(dtoAC, HttpStatus.CREATED);
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
