package com.banquito.core.account.controller;

import com.banquito.core.account.model.DebitCard;
import com.banquito.core.account.service.DebitCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debit-cards")
public class DebitCardController {

    @Autowired
    private DebitCardService debitCardService;

    @GetMapping("/by-card-number")
    public ResponseEntity<DebitCard> getDebitCardByCardNumber(@RequestParam String cardNumber) {
        try {
            DebitCard debitCard = debitCardService.obtainDebitCard(cardNumber);
            return new ResponseEntity<>(debitCard, HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
