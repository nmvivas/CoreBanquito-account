package com.banquito.core.account.controller.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;


import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountTransactionDTO {
    private Integer accountId;
    private String codeChannel;
    private String uniqueKey;
    private String transactionType;
    private String transactionSubtype;
    private String reference;
    private BigDecimal ammount;
    private String creditorAccount;
    private String debitorAccount;
    private Timestamp creationDate;
    private Boolean applyTax;
    private String parentTransactionKey;
    private String state;

}
