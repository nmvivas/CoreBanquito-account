package com.banquito.core.account.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import com.banquito.core.account.controller.dto.DebitCardDTO;
import com.banquito.core.account.model.DebitCard;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DebitCardMapper {
    DebitCardDTO toDTO(DebitCard debitCard);
}
