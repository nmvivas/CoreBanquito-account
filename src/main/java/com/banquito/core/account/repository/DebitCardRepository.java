package com.banquito.core.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banquito.core.account.model.DebitCard;

public interface DebitCardRepository extends JpaRepository<DebitCard,Integer>{
    Optional<DebitCard> findByCardNumber(String cardNumber);
}
