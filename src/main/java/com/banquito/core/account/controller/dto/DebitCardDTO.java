package com.banquito.core.account.controller.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DebitCardDTO {
    private Integer id;
    private Integer clientId;
    private Integer accountId;
    private String cardNumber;
    private String ccv;
    private Date expirationDate;
    private String cardholderName;
    private String cardUniqueKey;
    private String pin;
}
