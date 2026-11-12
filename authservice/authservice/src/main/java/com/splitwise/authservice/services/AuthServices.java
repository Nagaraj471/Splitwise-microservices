package com.splitwise.authservice.services;

import com.splitwise.authservice.dtos.LoginRequest;
import com.splitwise.authservice.dtos.LoginResponse;
import com.splitwise.authservice.dtos.RegisterRequest;
import com.splitwise.authservice.dtos.RegisterResponse;
import com.splitwise.authservice.entity.AuthUser;
import com.splitwise.authservice.exceptions.EmailAlreadyExistsException;
import com.splitwise.authservice.exceptions.EmailNotFoundException;
import com.splitwise.authservice.repositories.AuthUserRepo;
import com.splitwise.authservice.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServices {
    private final AuthUserRepo authUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterResponse register(RegisterRequest request){
        if (authUserRepo.existsByEmail(request.email())){
           throw new EmailAlreadyExistsException("Email already exists");
        }
        AuthUser user=new AuthUser();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        AuthUser savedUser=authUserRepo.save(user);
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getRole(),
                "User registered successfully"
        );
    }

    public LoginResponse login(LoginRequest request){
        AuthUser user=authUserRepo.findByEmail(request.email())
                .orElseThrow(()->new EmailNotFoundException("Email not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new BadCredentialsException("Invalid email or Password");
        }

        if (!user.isEnabled()){
            throw new BadCredentialsException("Invalid email or password");
        }
        String token = jwtService.generateToken(
                user.getId(),
                user.getRole()
        );
        return new LoginResponse(
                token,
                "BEARER",
                3600
        );
    }

}
