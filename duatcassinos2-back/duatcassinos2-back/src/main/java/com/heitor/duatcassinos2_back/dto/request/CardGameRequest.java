package com.heitor.duatcassinos2_back.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request para iniciar uma rodada no Jogo das Cartas
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardGameRequest {
    
    @NotNull(message = "ID do jogador é obrigatório")
    private Long playerId;
    
    @NotNull(message = "Valor da aposta é obrigatório")
    @Min(value = 5, message = "Aposta mínima é R$ 5,00")
    private Double betAmount;
}
