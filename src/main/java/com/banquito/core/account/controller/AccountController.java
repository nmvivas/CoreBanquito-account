package com.banquito.core.account.controller;

import com.banquito.core.account.controller.dto.AccountDTO;
import com.banquito.core.account.service.AccountService;
import com.banquito.core.account.util.mapper.AccountMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
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
            System.out.println("Va a buscar una cuenta por codigo unico:"+codeUniqueAccount);
            return ResponseEntity.ok(this.accountMapper.toDTO(this.service.obtainAccount(codeUniqueAccount)));
        } catch (RuntimeException rte) {
            return ResponseEntity.notFound().build();

        }
    }

    @GetMapping("/client-id/{clientId}")
    public ResponseEntity<AccountDTO> getAccountByClientId(@PathVariable Integer clientId) {
        try {
            System.out.println("Va a buscar una cuenta por id del cliente:"+ clientId);
            return ResponseEntity.ok(this.accountMapper.toDTO(this.service.obtainAccountByClientId(clientId)));
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.notFound().build();

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

