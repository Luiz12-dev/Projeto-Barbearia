package br.com.atividade.barbearia.Enum;

public enum StateAppoint {
    CANCELLED,
    CONFIRMED,
    COMPLETED,
    SCHEDULED;


    public String getDisplayName(){
        switch (this) {

            case CANCELLED: return "Cancelado";
            case CONFIRMED: return "Confirmado";
            case COMPLETED: return "Finalizado";
            case SCHEDULED: return "Agendado";
            default: return this.name();
        }
    }

    public boolean canBeCancelled(){
        return this == SCHEDULED || this == CONFIRMED;
    }

    public boolean isActive(){
        return this == SCHEDULED || this == CONFIRMED;
    }


}
