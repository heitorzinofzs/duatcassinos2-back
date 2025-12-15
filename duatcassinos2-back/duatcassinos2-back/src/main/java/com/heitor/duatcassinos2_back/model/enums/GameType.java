package com.heitor.duatcassinos2_back.model.enums;

public enum GameType {

    CARD_GAME("Jogo das Cartas", "Slot místico com símbolos egípcios"),
    NUMBER_GAME("Número do Faraó", "Adivinhe o número em até 5 tentativas"),
    SYNC_GAME("Sincronia Cósmica", "Clique no momento exato");
    
    private final String displayName;
    private final String description;
    
    GameType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }

}
