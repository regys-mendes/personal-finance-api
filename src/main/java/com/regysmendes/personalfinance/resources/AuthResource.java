package com.regysmendes.personalfinance.resources;

import com.regysmendes.personalfinance.dto.LoginDTO;
import com.regysmendes.personalfinance.dto.RefreshTokenDTO;
import com.regysmendes.personalfinance.dto.TokenResponseDTO;
import com.regysmendes.personalfinance.entities.RefreshToken;
import com.regysmendes.personalfinance.entities.User;
import com.regysmendes.personalfinance.services.RefreshTokenService;
import com.regysmendes.personalfinance.services.UserService;
import com.regysmendes.personalfinance.services.security.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private final UserService service;
    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;
    private final RefreshTokenService refreshService;

    public AuthResource(UserService service, PasswordEncoder passwordEncoder, TokenService tokenService, RefreshTokenService refreshService) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.refreshService = refreshService;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO dto) {
        User user = service.findByEmail(dto.getEmail());
        Boolean result = passwordEncoder.matches(dto.getPassword(), user.getPassword());

        if (!result) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            RefreshToken refreshToken = refreshService.createRefreshToken(user);
            String accessToken = tokenService.generateToken(user);
            TokenResponseDTO responseDTO = new TokenResponseDTO(accessToken, refreshToken.getTokenValue());
            return ResponseEntity.ok().body(responseDTO);
        }
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<String> refresh(@RequestBody RefreshTokenDTO refreshTokenValue) {
        User user = refreshService.validateRefreshToken(refreshTokenValue.getRefreshToken());
        String accessToken = tokenService.generateToken(user);
        return ResponseEntity.ok().body(accessToken);
    }


}
