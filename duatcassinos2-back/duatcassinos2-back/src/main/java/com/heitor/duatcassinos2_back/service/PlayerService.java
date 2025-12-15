package com.heitor.duatcassinos2_back.service;

import com.heitor.duatcassinos2_back.dto.response.PlayerResponse;
import com.heitor.duatcassinos2_back.model.Player;
import com.heitor.duatcassinos2_back.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service para gerenciar jogadores
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService {
    
    private final PlayerRepository playerRepository;
    
    /**
     * Cria um novo jogador (modo guest)
     */
    @Transactional
    public PlayerResponse createGuestPlayer(String nickname, Double initialBalance) {
        log.info("Criando jogador guest: {}", nickname);
        
        // Validar se nickname já existe
        if (playerRepository.existsByNickname(nickname)) {
            throw new RuntimeException("Nickname já está em uso!");
        }
        
        // Criar jogador
        Player player = new Player();
        player.setNickname(nickname);
        player.setBalance(initialBalance != null ? initialBalance : 1000.0);
        
        Player saved = playerRepository.save(player);
        
        return toResponse(saved);
    }
    
    /**
     * Busca jogador por ID
     */
    public PlayerResponse getPlayer(Long id) {
        Player player = playerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        
        return toResponse(player);
    }
    
    /**
     * Busca jogador por nickname
     */
    public PlayerResponse getPlayerByNickname(String nickname) {
        Player player = playerRepository.findByNickname(nickname)
            .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        
        return toResponse(player);
    }
    
    /**
     * Converte Player para PlayerResponse
     */
    private PlayerResponse toResponse(Player player) {
        return new PlayerResponse(
            player.getId(),
            player.getNickname(),
            player.getBalance(),
            player.getTotalGames(),
            player.getWins(),
            player.getLosses(),
            player.getWinRate()
        );
    }
}