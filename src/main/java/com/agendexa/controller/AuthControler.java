package com.agendexa.controller;

import com.agendexa.dto.LoginRequestDTO;
import com.agendexa.dto.LoginResponseDTO;
import com.agendexa.dto.RegisterRequestDTO;
import com.agendexa.entity.User;
import com.agendexa.repository.UserRepository;
import com.agendexa.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControler {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequestDTO data) {
        if (this.userRepository.findByEmail(data.email()).isPresent()) {
            return ResponseEntity.badRequest().build(); //email já existe
        }

        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(passwordEncoder.encode(data.password()));
        newUser.setRole(data.userRole()); //define perfil usuário

        this.userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
