package com.regysmendes.personalfinance.services;

import com.regysmendes.personalfinance.dto.UserDTO;
import com.regysmendes.personalfinance.entities.User;
import com.regysmendes.personalfinance.exceptions.ObjectNotFoundException;
import com.regysmendes.personalfinance.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email){
        Optional<User> user = repository.findByEmail(email);
        return user.orElseThrow(() -> new ObjectNotFoundException("User not found. Email" + email));
    }

    public User insert(UserDTO dto){
        User user = new User(null, dto.getName(), dto.getEmail(), dto.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return user;
    }

}
