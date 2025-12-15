package com.heitor.duatcassinos2_back.controller;

import com.heitor.duatcassinos2_back.dto.request.CardGameRequest;
import com.heitor.duatcassinos2_back.dto.response.CardGameResponse;
import com.heitor.duatcassinos2_back.service.CardGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller do Jogo das Cartas
 */
@RestController
@RequestMapping("/api/games/cards")
@RequiredArgsConstructor
public class CardGameController {
    
    private final CardGameService cardGameService;
    
    /**
     * Jogar uma rodada
     * POST /api/games/cards/play
     * Body: { "playerId": 1, "betAmount": 50.0 }
     */
    @PostMapping("/play")
    public ResponseEntity<CardGameResponse> playRound(@Valid @RequestBody CardGameRequest request) {
        CardGameResponse response = cardGameService.playRound(request);
        return ResponseEntity.ok(response);
    }
}
