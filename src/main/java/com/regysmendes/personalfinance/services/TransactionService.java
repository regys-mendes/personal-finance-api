package com.regysmendes.personalfinance.services;

import com.regysmendes.personalfinance.entities.Transaction;
import com.regysmendes.personalfinance.entities.TransactionType;
import com.regysmendes.personalfinance.repository.TransactionRepository;
import com.regysmendes.personalfinance.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<Transaction> findAll() {
        return repository.findAll();
    }

    public Transaction findById(Long id) {
        Optional<Transaction> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Resource not found. Id " + id));
    }

    public List<Transaction> findByType(TransactionType type) {
        return repository.findByTransactionType(type);
    }

    public Transaction insert(Transaction obj) {
        return repository.save(obj);
    }

    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    public Transaction update(Long id, Transaction obj) {
        Transaction entity = findById(id);
        updateTransaction(entity, obj);
        return repository.save(entity);
    }

    public void updateTransaction(Transaction entity, Transaction newData) {
        entity.setDescription(newData.getDescription());
        entity.setDate(newData.getDate());
        entity.setValue(newData.getValue());
        entity.setTransactionType(newData.getTransactionType());
    }

}
