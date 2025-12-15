package com.heitor.duatcassinos2_back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade que representa um jogador do cassino
 * Por enquanto sem sistema de login - apenas guest mode
 */
@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nickname;
    
    @Column(nullable = false)
    private Double balance; // Saldo atual
    
    @Column(nullable = false)
    private Integer totalGames; // Total de jogadas
    
    @Column(nullable = false)
    private Integer wins; // Total de vitórias
    
    @Column(nullable = false)
    private Integer losses; // Total de derrotas
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "last_played")
    private LocalDateTime lastPlayed;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (balance == null) {
            balance = 1000.0; // Saldo inicial padrão
        }
        if (totalGames == null) totalGames = 0;
        if (wins == null) wins = 0;
        if (losses == null) losses = 0;
    }
    
    @PreUpdate
    protected void onUpdate() {
        lastPlayed = LocalDateTime.now();
    }
    
    // Métodos auxiliares
    public void addBalance(Double amount) {
        this.balance += amount;
    }
    
    public void subtractBalance(Double amount) {
        this.balance -= amount;
    }
    
    public void incrementWins() {
        this.wins++;
        this.totalGames++;
    }
    
    public void incrementLosses() {
        this.losses++;
        this.totalGames++;
    }
    
    public double getWinRate() {
        if (totalGames == 0) return 0.0;
        return (double) wins / totalGames * 100;
    }
}