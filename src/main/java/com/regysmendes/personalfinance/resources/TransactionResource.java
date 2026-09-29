package com.regysmendes.personalfinance.resources;

import com.regysmendes.personalfinance.dto.transaction.TransactionInsertDTO;
import com.regysmendes.personalfinance.dto.transaction.TransactionResponseDTO;
import com.regysmendes.personalfinance.dto.transaction.TransactionUpdateDTO;
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
    public ResponseEntity<List<TransactionResponseDTO>> findAll(){
       List<TransactionResponseDTO> obj = service.findAll();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransactionResponseDTO> findById(@PathVariable Long id){
        TransactionResponseDTO obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/type/{type}")
    public ResponseEntity<List<TransactionResponseDTO>> findByType(@PathVariable TransactionType type){
      List<TransactionResponseDTO> obj = service.findByType(type);
      return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> insert(@Valid @RequestBody TransactionInsertDTO obj){
      TransactionResponseDTO responseDTO = service.insert(obj);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable Long id, @Valid @RequestBody TransactionUpdateDTO obj){
        TransactionResponseDTO responseDTO = service.update(id, obj);
        return ResponseEntity.ok().body(responseDTO);
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
