package com.heitor.duatcassinos2_back.dto.response;

import com.heitor.duatcassinos2_back.model.enums.CardSymbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Response de uma rodada do Jogo das Cartas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardGameResponse {
    
    private Long playerId;
    private Double betAmount;
    private List<CardSymbol> symbols; // 3 símbolos sorteados
    private Boolean won;
    private Double winAmount; // Valor ganho (ou 0 se perdeu)
    private Double multiplier; // Multiplicador aplicado
    private Double newBalance; // Novo saldo do jogador
    private String message; // Mensagem explicativa
    
    // Estatísticas extras
    private Integer totalGames;
    private Integer wins;
    private Integer losses;
    private Double winRate;
}