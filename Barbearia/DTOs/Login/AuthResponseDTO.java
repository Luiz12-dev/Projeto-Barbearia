package br.com.atividade.barbearia.DTOs.Login;

public record AuthResponseDTO(

        String token,
        String type

) {

    public AuthResponseDTO(String token){
        this(token, "Bearer");
    }
}
