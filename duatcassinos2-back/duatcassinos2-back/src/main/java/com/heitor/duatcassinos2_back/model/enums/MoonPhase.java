package com.heitor.duatcassinos2_back.model.enums;

public enum MoonPhase {

    NEW_MOON("Lua Nova", 0.15, 3.0, 2.0, "🌑"),
    FULL_MOON("Lua Cheia", 0.25, 2.0, 1.0, "🌕"),
    CRESCENT("Lua Crescente", 0.30, 1.2, 0.5, "🌒"),
    WANING("Lua Minguante", 0.30, 1.2, 0.5, "🌘");

    private final String displayName;
    private final double probability;
    private final double winMultiplier;
    private final double lossMultiplier;
    private final String emoji;

    MoonPhase(String displayName, double probability, double winMultiplier, double lossMultiplier, String emoji) {
        this.displayName = displayName;
        this.probability = probability;
        this.winMultiplier = winMultiplier;
        this.lossMultiplier = lossMultiplier;
        this.emoji = emoji;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getProbability() {
        return probability;
    }

    public double getWinMultiplier() {
        return winMultiplier;
    }

    public double getLossMultiplier() {
        return lossMultiplier;
    }

    public String getEmoji() {
        return emoji;
    }
}
