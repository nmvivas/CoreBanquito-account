package com.banquito.core.account.model;

import java.io.Serializable;
import java.sql.Date;
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
@Table(name = "DEBIT_CARD")
public class DebitCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEBIT_CARD_ID", nullable = false)
    private Integer id;
    @Column(name = "CLIENT_ID", nullable = false)
    private Integer clientId;
    @Column(name = "ACCOUNT_ID", nullable = false)
    private Integer accountId;
    @Column(name = "CARD_NUMBER", length = 20, nullable = false)
    private String cardNumber;
    @Column(name = "CCV", length = 3, nullable = false)
    private String ccv;
    @Temporal(TemporalType.DATE)
    @Column(name = "EXPIRATION_DATE", nullable = false)
    private Date expirationDate;
    @Column(name = "CARDHOLDER_NAME", length = 50, nullable = false)
    private String cardholderName;
    @Column(name = "CARD_UNIQUE_KEY", length = 32, nullable = false)
    private String cardUniqueKey;
    @Column(name = "PIN", length = 64, nullable = false)
    private String pin;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID", insertable = false, updatable = false)
    private Account account;

    public DebitCard(Integer id) {
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
        DebitCard other = (DebitCard) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
