package com.heitor.duatcassinos2_back.controller;

import com.heitor.duatcassinos2_back.dto.response.PlayerResponse;
import com.heitor.duatcassinos2_back.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller para gerenciar jogadores
 */
@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerController {
    
    private final PlayerService playerService;
    
    /**
     * Criar jogador guest
     * POST /api/players/guest?nickname=NomeJogador&balance=1000
     */
    @PostMapping("/guest")
    public ResponseEntity<PlayerResponse> createGuest(
            @RequestParam String nickname,
            @RequestParam(required = false) Double balance) {
        
        PlayerResponse player = playerService.createGuestPlayer(nickname, balance);
        return ResponseEntity.ok(player);
    }
    
    /**
     * Buscar jogador por ID
     * GET /api/players/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable Long id) {
        PlayerResponse player = playerService.getPlayer(id);
        return ResponseEntity.ok(player);
    }
    
    /**
     * Buscar jogador por nickname
     * GET /api/players/by-nickname?nickname=NomeJogador
     */
    @GetMapping("/by-nickname")
    public ResponseEntity<PlayerResponse> getPlayerByNickname(@RequestParam String nickname) {
        PlayerResponse player = playerService.getPlayerByNickname(nickname);
        return ResponseEntity.ok(player);
    }
}
