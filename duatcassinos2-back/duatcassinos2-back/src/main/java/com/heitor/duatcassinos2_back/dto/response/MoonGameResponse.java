package com.heitor.duatcassinos2_back.dto.response;

import com.heitor.duatcassinos2_back.model.enums.MoonPhase;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoonGameResponse {

    private Long playerId;

    private MoonPhase chosenPhase;
    private MoonPhase finalPhase;

    private Boolean won;

    private Double amountChange;
    private Double newBalance;

    private String message;

    private Integer totalGames;
    private Integer wins;
    private Integer losses;
    private Double winRate;
}
