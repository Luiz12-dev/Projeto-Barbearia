package br.com.atividade.barbearia.Controllers;

import br.com.atividade.barbearia.DTOs.Login.AuthResponseDTO;
import br.com.atividade.barbearia.DTOs.Login.LoginDTO;
import br.com.atividade.barbearia.DTOs.Register.RegisterDTO;
import br.com.atividade.barbearia.DTOs.Register.RegisterResponseDTO;
import br.com.atividade.barbearia.security.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(@Valid @RequestBody RegisterDTO registerDTO){

     RegisterResponseDTO response = authService.register(registerDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }



    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(@Valid @RequestBody LoginDTO loginDTO){
       AuthResponseDTO authResponseDTO = authService.login(loginDTO);

       return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
    }
}
