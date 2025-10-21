package br.com.atividade.barbearia.security.service;

import br.com.atividade.barbearia.DTOs.Login.AuthResponseDTO;
import br.com.atividade.barbearia.DTOs.Login.LoginDTO;
import br.com.atividade.barbearia.DTOs.Register.RegisterDTO;
import br.com.atividade.barbearia.DTOs.Register.RegisterResponseDTO;
import br.com.atividade.barbearia.Model.User;
import br.com.atividade.barbearia.Repository.UserRepository;
import br.com.atividade.barbearia.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }



    @Transactional
    public RegisterResponseDTO register(RegisterDTO registerDTO) {
        if(userRepository.findByUsername(registerDTO.username()).isPresent()){
            throw new RuntimeException("Username already exists");
        }

        if(userRepository.findByEmail(registerDTO.email()).isPresent()){
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .username(registerDTO.username())
                .email(registerDTO.email())
                .password(passwordEncoder.encode(registerDTO.password()))
                .role(registerDTO.role())
                .build();

        User savedUser = userRepository.save(user);

        return  RegisterResponseDTO.success(savedUser);



    }


    public AuthResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = jwtUtils.generateJwtToken(authentication);

                return new AuthResponseDTO(token);

    }
}
