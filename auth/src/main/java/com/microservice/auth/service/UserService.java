package com.microservice.auth.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.auth.dto.request.CreateUserDto;
import com.microservice.auth.dto.request.LoginUserDto;
import com.microservice.auth.dto.response.TokenDto;
import com.microservice.auth.entity.User;
import com.microservice.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService service;
    private final AuthenticationManager authenticationManager;
    
    @Transactional 
    public String createUser(CreateUserDto dto) {
        String password = passwordEncoder.encode(dto.password());
        User user = new User(dto, password);

        repository.save(user);
        return "criado com sucesso";
    }
    
    @Transactional(readOnly = true)
    public TokenDto login(LoginUserDto dto) {
        var authentication = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication auth = authenticationManager.authenticate(authentication);
        
        Integer userId = repository.getIdByEmail(dto.email());

        Map<String, String> accessToken = service.generatedAcessToken(auth, userId);
        Map<String, String> refreshToken = service.generatedRefreshToken(auth, userId); 

        return new TokenDto(
            accessToken.get("Access token"),
            accessToken.get("expiry"),
            refreshToken.get("Refresh token"),
            refreshToken.get("expiry")
        );
    }

    public Map<String, String> validationToken(String token) {
        String formattedToken = token.replace("Bearer", "");
        return service.generatedNewAccessToken(formattedToken);
    }

}
