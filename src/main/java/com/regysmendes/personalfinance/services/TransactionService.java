package com.regysmendes.personalfinance.services;

import com.regysmendes.personalfinance.dto.transaction.TransactionInsertDTO;
import com.regysmendes.personalfinance.dto.transaction.TransactionResponseDTO;
import com.regysmendes.personalfinance.dto.transaction.TransactionUpdateDTO;
import com.regysmendes.personalfinance.entities.Transaction;
import com.regysmendes.personalfinance.entities.TransactionType;
import com.regysmendes.personalfinance.entities.User;
import com.regysmendes.personalfinance.exceptions.ForbiddenException;
import com.regysmendes.personalfinance.exceptions.ObjectNotFoundException;
import com.regysmendes.personalfinance.repository.TransactionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public List<TransactionResponseDTO> findAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Transaction> transactions = repository.findByUserId(user.getId());
        List<TransactionResponseDTO> result = new ArrayList<>();

        for (Transaction t : transactions) {
            TransactionResponseDTO responseDTO = new TransactionResponseDTO(t.getId(), t.getDescription(), t.getValue(), t.getDate(), t.getTransactionType());
            result.add(responseDTO);
        }
        return result;
    }

    public Transaction findByIdentity(Long id) {
        Optional<Transaction> obj = repository.findById(id);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Transaction transaction = obj.orElseThrow(() -> new ObjectNotFoundException("Id not found: " + id));

        if (transaction.getUser().getId().equals(user.getId())){
            return transaction;
        }
        else throw new ForbiddenException("Access Denied!");
    }

    public TransactionResponseDTO findById(Long id) {
        Transaction transaction = findByIdentity(id);
        return new TransactionResponseDTO(transaction.getId(), transaction.getDescription(), transaction.getValue(), transaction.getDate(), transaction.getTransactionType());
    }

    public List<TransactionResponseDTO> findByType(TransactionType type) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Transaction> transaction = repository.findByTransactionTypeAndUserId(type, user.getId());
        List<TransactionResponseDTO> result = new ArrayList<>();

        for (Transaction t : transaction) {
            TransactionResponseDTO responseDTO = new TransactionResponseDTO(t.getId(), t.getDescription(), t.getValue(), t.getDate(), t.getTransactionType());
            result.add(responseDTO);
        }
        return result;
    }

    public TransactionResponseDTO insert(TransactionInsertDTO dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Transaction transaction = new Transaction(null, dto.getDescription(), dto.getValue(), dto.getDate(), dto.getTransactionType(), user);

        repository.save(transaction);
        return new TransactionResponseDTO(transaction.getId(), transaction.getDescription(), transaction.getValue(), transaction.getDate(), transaction.getTransactionType());
    }

    public void delete(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    public TransactionResponseDTO update(Long id, TransactionUpdateDTO obj) {
        Transaction entity = findByIdentity(id);
        updateTransaction(entity, obj);
        repository.save(entity);
        return new TransactionResponseDTO(entity.getId(), entity.getDescription(), entity.getValue(), entity.getDate(), entity.getTransactionType());
    }

    public void updateTransaction(Transaction entity, TransactionUpdateDTO newData) {
        entity.setDescription(newData.getDescription());
        entity.setDate(newData.getDate());
        entity.setValue(newData.getValue());
        entity.setTransactionType(newData.getTransactionType());
    }

    public BigDecimal getBalance() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Transaction> despesas = repository.findByTransactionTypeAndUserId(TransactionType.DESPESA, user.getId());
        List<Transaction> receitas = repository.findByTransactionTypeAndUserId(TransactionType.RECEITA, user.getId());

        BigDecimal totalIncome = BigDecimal.ZERO;

        for (Transaction t : receitas) {
            totalIncome = totalIncome.add(t.getValue());
        }

        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Transaction t : despesas) {
            totalExpenses = totalExpenses.add(t.getValue());
        }

        return totalIncome.subtract(totalExpenses);

    }
}
