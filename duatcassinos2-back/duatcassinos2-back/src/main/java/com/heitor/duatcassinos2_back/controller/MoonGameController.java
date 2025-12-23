package com.heitor.duatcassinos2_back.controller;

import com.heitor.duatcassinos2_back.dto.request.MoonGameRequest;
import com.heitor.duatcassinos2_back.dto.response.MoonGameResponse;
import com.heitor.duatcassinos2_back.service.MoonGameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games/moon")
@RequiredArgsConstructor
public class MoonGameController {

    private final MoonGameService moonGameService;

    @PostMapping("/play")
    public ResponseEntity<MoonGameResponse> play(@Valid @RequestBody MoonGameRequest request) {
        return ResponseEntity.ok(moonGameService.play(request));
    }
}
