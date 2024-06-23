package com.banquito.core.account.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import com.banquito.core.account.controller.dto.AccountDTO;
import com.banquito.core.account.model.Account;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountDTO toDTO(Account account);

    Account toPersistence(AccountDTO dto);
}
