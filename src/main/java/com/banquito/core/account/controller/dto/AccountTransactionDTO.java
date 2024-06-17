package com.banquito.core.account.controller.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AccountTransactionDTO {
    private String transactionType;
    private String transactionSubtype;
    private String reference;
    private BigDecimal amount;
    private String creditorBankCode;
    private String creditorAccount;
    private String debitorBankCode;
    private String debitorAccount;
    private Timestamp creationDate;
    private Timestamp bookingDate;
    private Timestamp valueDate;
    private Boolean applyTax;
    private String parentTransactionKey;
    private String state;

}
