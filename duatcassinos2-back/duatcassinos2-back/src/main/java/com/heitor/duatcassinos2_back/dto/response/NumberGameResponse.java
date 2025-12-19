package com.heitor.duatcassinos2_back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumberGameResponse {
    
    private String sessionId; // ID da sessão do jogo
    private Long playerId;
    private Double betAmount;
    private Integer guess; // Número que o jogador chutou
    private Integer attemptsLeft; // Tentativas restantes
    private Boolean won; // Ganhou?
    private Boolean gameOver; // Jogo acabou?
    private String hint; // "MAIOR", "MENOR", ou null
    private Double winAmount; // Valor ganho
    private Double newBalance; // Novo saldo
    private String message; // Mensagem explicativa
    
    private Integer totalGames;
    private Integer wins;
    private Integer losses;
    private Double winRate;
}