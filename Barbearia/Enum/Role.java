package br.com.atividade.barbearia.Enum;

public enum Role {
    USER("Cliente"),
    OWNER ("Proprietario");

    private final String displayName;

     Role (String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName(){
         return this.displayName;
    }

    public boolean isOwner(){
         return this == OWNER;
    }


    public boolean isUser(){
         return this == USER;
    }

    public String getAuthority(){
        return "Role_" + this.name();
    }
}
