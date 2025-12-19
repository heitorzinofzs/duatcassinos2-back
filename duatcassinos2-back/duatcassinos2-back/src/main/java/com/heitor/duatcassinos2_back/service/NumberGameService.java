package com.heitor.duatcassinos2_back.service;

import com.heitor.duatcassinos2_back.dto.request.NumberGameRequest;
import com.heitor.duatcassinos2_back.dto.response.NumberGameResponse;
import com.heitor.duatcassinos2_back.model.Player;
import com.heitor.duatcassinos2_back.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class NumberGameService {
    
    private final PlayerRepository playerRepository;
    private final Random random = new Random();
   
    private final Map<String, GameSession> activeSessions = new ConcurrentHashMap<>();
    
    @Transactional
    public NumberGameResponse playRound(NumberGameRequest request) {
        log.info("Tentativa - Player: {}, Guess: {}, Session: {}", 
                 request.getPlayerId(), request.getGuess(), request.getSessionId());
        
        Player player = playerRepository.findById(request.getPlayerId())
            .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        
        GameSession session;
        boolean isNewGame = false;
        
        if (request.getSessionId() == null || !activeSessions.containsKey(request.getSessionId())) {
            // Nova partida
            if (player.getBalance() < request.getBetAmount()) {
                throw new RuntimeException("Saldo insuficiente!");
            }
            
            String sessionId = UUID.randomUUID().toString();
            int secretNumber = random.nextInt(100) + 1; // 1 a 100
            
            session = new GameSession(sessionId, secretNumber, 5, request.getBetAmount());
            activeSessions.put(sessionId, session);
            isNewGame = true;
            
            log.info("Nova sessão criada - SessionId: {}, Número secreto: {}", sessionId, secretNumber);
        } else {
            
            session = activeSessions.get(request.getSessionId());
        }
        
        session.attemptsLeft--;
        boolean won = (request.getGuess() == session.secretNumber);
        String hint = null;
        
        if (!won && session.attemptsLeft > 0) {
            
            hint = (request.getGuess() < session.secretNumber) ? "MAIOR" : "MENOR";
        }
        
        boolean gameOver = (won || session.attemptsLeft == 0);
        
        double winAmount = 0.0;
        String message;
        
        if (won) {
            
            int attemptNumber = 5 - session.attemptsLeft;
            double multiplier = getMultiplier(attemptNumber);
            winAmount = session.betAmount * multiplier;
            
            message = String.format("🎉 ACERTOU na %dª tentativa! Multiplicador: %.1fx", 
                                   attemptNumber, multiplier);
            
            player.addBalance(winAmount);
            player.incrementWins();
            
        } else if (gameOver) {
            
            message = "😔 Acabaram as tentativas! O número era: " + session.secretNumber;
            player.subtractBalance(session.betAmount);
            player.incrementLosses();
            
        } else {
            
            message = String.format("Tente novamente! O número é %s. Tentativas restantes: %d", 
                                   hint, session.attemptsLeft);
        }
        
        if (gameOver) {
            activeSessions.remove(session.sessionId);
            playerRepository.save(player);
        }
        
        return NumberGameResponse.builder()
            .sessionId(session.sessionId)
            .playerId(player.getId())
            .betAmount(session.betAmount)
            .guess(request.getGuess())
            .attemptsLeft(session.attemptsLeft)
            .won(won)
            .gameOver(gameOver)
            .hint(hint)
            .winAmount(winAmount)
            .newBalance(player.getBalance())
            .message(message)
            .totalGames(player.getTotalGames())
            .wins(player.getWins())
            .losses(player.getLosses())
            .winRate(player.getWinRate())
            .build();
    }
    
    
    private double getMultiplier(int attemptNumber) {
        return switch (attemptNumber) {
            case 1 -> 10.0;  // 1ª tentativa: 10x
            case 2 -> 6.0;   // 2ª tentativa: 6x
            case 3 -> 4.0;   // 3ª tentativa: 4x
            case 4 -> 2.0;   // 4ª tentativa: 2x
            case 5 -> 1.5;   // 5ª tentativa: 1.5x
            default -> 1.0;
        };
    }
    
    
    private static class GameSession {
        String sessionId;
        int secretNumber;
        int attemptsLeft;
        double betAmount;
        
        GameSession(String sessionId, int secretNumber, int attemptsLeft, double betAmount) {
            this.sessionId = sessionId;
            this.secretNumber = secretNumber;
            this.attemptsLeft = attemptsLeft;
            this.betAmount = betAmount;
        }
    }
}