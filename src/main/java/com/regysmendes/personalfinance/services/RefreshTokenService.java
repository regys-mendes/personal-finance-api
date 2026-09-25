package com.regysmendes.personalfinance.services;

import com.regysmendes.personalfinance.dto.UserDTO;
import com.regysmendes.personalfinance.entities.RefreshToken;
import com.regysmendes.personalfinance.entities.User;
import com.regysmendes.personalfinance.exceptions.ObjectNotFoundException;
import com.regysmendes.personalfinance.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public RefreshToken createRefreshToken(User user){
       RefreshToken refreshToken = new RefreshToken(null, UUID.randomUUID().toString(), LocalDateTime.now().plusDays(7), user);
       return repository.save(refreshToken);
    }

    public User validateRefreshToken(String tokenValue){
       Optional<RefreshToken> refreshToken = repository.findByTokenValue(tokenValue);
       RefreshToken token = refreshToken.orElseThrow(() -> new ObjectNotFoundException("Refresh token not found"));

       if (token.getExpiration().isBefore(LocalDateTime.now())){
           throw new RuntimeException("Refresh token expired");
       }
       return token.getUser();
    }


}
