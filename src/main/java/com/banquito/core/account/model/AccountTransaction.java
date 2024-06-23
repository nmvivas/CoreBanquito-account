package com.banquito.core.account.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "ACCOUNT_TRANSACTION")
public class AccountTransaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_TRANSACTION_ID", nullable = false)
    private Integer id;

    @Column(name = "ACCOUNT_ID", nullable = false)
    private Integer accountId;

    @Column(name = "CODE_CHANNEL", length = 10, nullable = false)
    private String codeChannel;

    @Column(name = "UNIQUE_KEY", length = 32, nullable = false)
    private String uniqueKey;

    @Column(name = "TRANSACTION_TYPE", length = 3, nullable = false)
    private String transactionType;

    @Column(name = "TRANSACTION_SUBTYPE", length = 12, nullable = false)
    private String transactionSubtype;

    @Column(name = "REFERENCE", length = 50, nullable = false)
    private String reference;

    @Column(name = "AMMOUNT", precision = 17, scale = 2, nullable = false)
    private BigDecimal ammount;

    @Column(name = "CREDITOR_BANK_CODE", length = 20)
    private String creditorBankCode;

    @Column(name = "CREDITOR_ACCOUNT", length = 16)
    private String creditorAccount;

    @Column(name = "DEBTOR_BANK_CODE", length = 20)
    private String debitorBankCode;

    @Column(name = "DEBTOR_ACCOUNT", length = 16)
    private String debitorAccount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATION_DATE", nullable = false)
    private Timestamp creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BOOKING_DATE", nullable = false)
    private Timestamp bookingDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VALUE_DATE", nullable = false)
    private Timestamp valueDate;

    @Column(name = "APPLY_TAX", nullable = false)
    private Boolean applyTax;

    @Column(name = "PARENT_TRANSACTION_KEY", length = 32)
    private String parentTransactionKey;

    @Column(name = "STATE", length = 3, nullable = false)
    private String state;

    @Column(name = "NOTES", length = 200)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID", insertable = false, updatable = false)
    private Account account;

    public AccountTransaction(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AccountTransaction other = (AccountTransaction) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
