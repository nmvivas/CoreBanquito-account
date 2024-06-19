package com.banquito.core.account.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.core.account.controller.dto.AccountTransactionDTO;
import com.banquito.core.account.model.AccountTransaction;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountTransactionMapper {
    AccountTransactionDTO toDTO(AccountTransaction accountTransaction);
    AccountTransaction toPersistence(AccountTransactionDTO accountTransactionDTO);
}
