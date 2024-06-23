package com.banquito.core.account.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.banquito.core.account.controller.dto.AccountDTO;
import com.banquito.core.account.model.Account;
import com.banquito.core.account.repository.AccountRepository;
import com.banquito.core.account.util.mapper.AccountMapper;

@Service
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository repository, AccountMapper accountMapper) {
        this.repository = repository;
        this.accountMapper = accountMapper;
    }

    public Account obtainAccount(String codeUniqueAccount) {
        Optional<Account> accountOpt = this.repository.findByCodeUniqueAccount(codeUniqueAccount);
        if (accountOpt.isPresent()) {
            return accountOpt.get();
        } else {
            throw new RuntimeException("No existe la ceunta con el numero" + codeUniqueAccount);
        }
    }

    public AccountDTO create(AccountDTO dto) {

        Optional <Account> codeUnique = this.repository.findByCodeUniqueAccount(dto.getCodeUniqueAccount());
        if (codeUnique.isPresent()) {
            throw new RuntimeException("Código único repetido");
        }
        // SE DEJA ESTE PEDAZO DE CODIGO PARA CUANDO SE CREE DTO de envio
        // if (dto.getCodeProductType() == null || dto.getCodeProduct() == null) {
        //     throw new RuntimeException("CodeProductType y CodeProduct son obligatorios");
        // }
        dto = generateAccountCodesAndNumbers(dto);

        Account account = this.accountMapper.toPersistence(dto);
        account.setCreationDate(LocalDateTime.now());
        account.setLastModifiedDate(LocalDateTime.now());
        account.setState("INA");
        Account accountCreated = this.repository.save(account);
        return this.accountMapper.toDTO(accountCreated);
    }

    public Account saveAccount(Account account) {
        return repository.save(account);
    }

    private AccountDTO generateAccountCodesAndNumbers(AccountDTO dto) {
        return AccountDTO.builder()
            .id(dto.getId())
            .clientId(dto.getClientId())
            .codeUniqueAccount(generateUniqueAccountCode())
            .codeInternalAccount(generateInternalAccountCode())
            .codeInternationalAccount(generateInternationalAccountCode())
            .number(generateAccountNumber())
            .state("INA")
            .currentBalance(dto.getCurrentBalance())
            .availableBalance(dto.getAvailableBalance())
            .blockedBalance(dto.getBlockedBalance())
            .build();
    }

    private String generateUniqueAccountCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 32).toUpperCase();
    }

    private String generateInternalAccountCode() {
        return String.format("%010d", ThreadLocalRandom.current().nextInt(1, 1000000000));
    }

    private String generateInternationalAccountCode() {
        return "INT" + String.format("%013d", ThreadLocalRandom.current().nextLong(1, 10000000000000L));
    }

    private String generateAccountNumber() {
        return String.format("%013d", ThreadLocalRandom.current().nextLong(1, 10000000000000L));
    }

}
