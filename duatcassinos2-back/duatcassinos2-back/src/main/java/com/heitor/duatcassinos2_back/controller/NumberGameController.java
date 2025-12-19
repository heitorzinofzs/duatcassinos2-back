package com.heitor.duatcassinos2_back.controller;

import com.heitor.duatcassinos2_back.dto.request.NumberGameRequest;
import com.heitor.duatcassinos2_back.dto.response.NumberGameResponse;
import com.heitor.duatcassinos2_back.service.NumberGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/games/number")
@RequiredArgsConstructor
public class NumberGameController {
    
    private final NumberGameService numberGameService;
    
    @PostMapping("/play")
    public ResponseEntity<NumberGameResponse> playRound(@Valid @RequestBody NumberGameRequest request) {
        NumberGameResponse response = numberGameService.playRound(request);
        return ResponseEntity.ok(response);
    }
}