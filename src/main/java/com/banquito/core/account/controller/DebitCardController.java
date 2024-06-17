package com.banquito.core.account.controller;

import com.banquito.core.account.controller.dto.DebitCardDTO;
import com.banquito.core.account.model.DebitCard;
import com.banquito.core.account.service.DebitCardService;
import com.banquito.core.account.util.mapper.DebitCardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
@RestController
@RequestMapping("/debit-cards")
public class DebitCardController {

    @Autowired
    private final DebitCardMapper debitCardMapper;
    private DebitCardService service;

    public DebitCardController(DebitCardMapper debitCardMapper, DebitCardService service){
        this.debitCardMapper = debitCardMapper;
        this.service = service;
    }
    @GetMapping("/by-card-number/{cardNumber}")
    public ResponseEntity<DebitCardDTO> getDebitCardByCardNumber(@PathVariable String cardNumber) {
        try {
            System.out.println("Va a buscar una tarjeta por el numero:"+cardNumber);
            return ResponseEntity.ok(this.debitCardMapper.toDTO(this.service.obtainDebitCard(cardNumber)));
        } catch (RuntimeException rte) {
            rte.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}
