package com.regysmendes.personalfinance.resources;

import com.regysmendes.personalfinance.entities.Transaction;
import com.regysmendes.personalfinance.entities.TransactionType;
import com.regysmendes.personalfinance.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
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

    @PostMapping
    public ResponseEntity<Transaction> insert(@Valid @RequestBody Transaction obj){
      Transaction newObj = service.insert(obj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newObj.getId())
                .toUri();

        return ResponseEntity.created(uri).body(newObj);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id, @Valid @RequestBody Transaction obj){
        Transaction newObj = service.update(id, obj);

        return ResponseEntity.ok().body(newObj);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
         service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/balance")
    public ResponseEntity<BigDecimal> getBalance(){
        BigDecimal balance = service.getBalance();
        return ResponseEntity.ok().body(balance);
    }

}
