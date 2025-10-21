package br.com.atividade.barbearia.Exception;

public class TimeSlotUnavailableException extends RuntimeException {
    public TimeSlotUnavailableException(String message) {
        super(message);
    }
}
