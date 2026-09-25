package com.regysmendes.personalfinance.resources;

import com.regysmendes.personalfinance.dto.LoginDTO;
import com.regysmendes.personalfinance.entities.User;
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

    public AuthResource(UserService service, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        User user = service.findByEmail(dto.getEmail());
        Boolean result = passwordEncoder.matches(dto.getPassword(), user.getPassword());
        if (! result){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else return ResponseEntity.ok().body(tokenService.generateToken(user));
    }
}
