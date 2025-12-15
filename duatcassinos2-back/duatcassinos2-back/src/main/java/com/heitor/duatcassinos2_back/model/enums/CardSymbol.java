package com.heitor.duatcassinos2_back.model.enums;

public enum CardSymbol {
    FLAME("🔥", "Chama Dourada", 2.0),
    ANKH("☥", "Ankh", 3.0),
    EYE("👁", "Olho de Hórus", 4.0),
    SCARAB("🐞", "Escaravelho", 5.0),
    MOON("🌙", "Lua Crescente", 6.0),
    STAR("⭐", "Estrela Dourada", 10.0);
    
    private final String symbol;
    private final String name;
    private final double multiplier;
    
    CardSymbol(String symbol, String name, double multiplier) {
        this.symbol = symbol;
        this.name = name;
        this.multiplier = multiplier;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public String getName() {
        return name;
    }
    
    public double getMultiplier() {
        return multiplier;
    }
}
