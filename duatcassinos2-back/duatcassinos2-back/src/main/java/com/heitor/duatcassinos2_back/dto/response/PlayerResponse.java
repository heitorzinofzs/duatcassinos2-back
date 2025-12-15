package com.heitor.duatcassinos2_back.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response de informações do jogador
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponse {
    
    private Long id;
    private String nickname;
    private Double balance;
    private Integer totalGames;
    private Integer wins;
    private Integer losses;
    private Double winRate;
}