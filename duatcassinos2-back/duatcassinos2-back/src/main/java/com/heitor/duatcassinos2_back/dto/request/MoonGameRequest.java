package com.heitor.duatcassinos2_back.dto.request;

import com.heitor.duatcassinos2_back.model.enums.MoonPhase;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MoonGameRequest {

    @NotNull
    private Long playerId;

    @NotNull
    @Min(10)
    private Double betAmount;

    @NotNull
    private MoonPhase chosenPhase;
}
