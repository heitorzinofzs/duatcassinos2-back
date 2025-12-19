package com.heitor.duatcassinos2_back.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NumberGameRequest {
    
    @NotNull(message = "ID do jogador é obrigatório")
    private Long playerId;
    
    @NotNull(message = "Valor da aposta é obrigatório")
    @Min(value = 10, message = "Aposta mínima é R$ 10,00")
    private Double betAmount;
    
    @NotNull(message = "Número escolhido é obrigatório")
    @Min(value = 1, message = "Número deve ser entre 1 e 100")
    @Max(value = 100, message = "Número deve ser entre 1 e 100")
    private Integer guess;
    
    private String sessionId;
}