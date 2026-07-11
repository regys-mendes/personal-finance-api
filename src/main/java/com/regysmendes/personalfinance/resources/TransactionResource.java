package com.regysmendes.personalfinance.resources;

import com.regysmendes.personalfinance.entities.Transaction;
import com.regysmendes.personalfinance.entities.TransactionType;
import com.regysmendes.personalfinance.services.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionResource {

    private final TransactionService service;

    public TransactionResource(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> findAll(){
       List<Transaction> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id){
        Transaction obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<List<Transaction>> findByType(@PathVariable TransactionType type){
      List<Transaction> obj = service.findByType(type);
      return ResponseEntity.ok().body(obj);
    }

}
