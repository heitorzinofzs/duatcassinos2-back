package com.heitor.duatcassinos2_back.service;

import com.heitor.duatcassinos2_back.dto.request.CardGameRequest;
import com.heitor.duatcassinos2_back.dto.response.CardGameResponse;
import com.heitor.duatcassinos2_back.model.Player;
import com.heitor.duatcassinos2_back.model.enums.CardSymbol;
import com.heitor.duatcassinos2_back.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service com toda a lógica do Jogo das Cartas
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CardGameService {
    
    private final PlayerRepository playerRepository;
    private final Random random = new Random();
    
    /**
     * Executa uma rodada do jogo de cartas
     */
    @Transactional
    public CardGameResponse playRound(CardGameRequest request) {
        log.info("Iniciando rodada - Player: {}, Aposta: {}", 
                 request.getPlayerId(), request.getBetAmount());
        
        // 1. Buscar jogador
        Player player = playerRepository.findById(request.getPlayerId())
            .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));
        
        // 2. Validar saldo
        if (player.getBalance() < request.getBetAmount()) {
            throw new RuntimeException("Saldo insuficiente!");
        }
        
        // 3. Sortear 3 símbolos
        List<CardSymbol> symbols = drawSymbols();
        
        // 4. Calcular resultado
        GameResult result = calculateResult(symbols, request.getBetAmount());
        
        // 5. Atualizar saldo do jogador
        if (result.won) {
            player.addBalance(result.winAmount);
            player.incrementWins();
        } else {
            player.subtractBalance(request.getBetAmount());
            player.incrementLosses();
        }
        
        playerRepository.save(player);
        
        // 6. Construir resposta
        return CardGameResponse.builder()
            .playerId(player.getId())
            .betAmount(request.getBetAmount())
            .symbols(symbols)
            .won(result.won)
            .winAmount(result.winAmount)
            .multiplier(result.multiplier)
            .newBalance(player.getBalance())
            .message(result.message)
            .totalGames(player.getTotalGames())
            .wins(player.getWins())
            .losses(player.getLosses())
            .winRate(player.getWinRate())
            .build();
    }
    
    /**
     * Sorteia 3 símbolos aleatórios
     */
    private List<CardSymbol> drawSymbols() {
        List<CardSymbol> symbols = new ArrayList<>();
        CardSymbol[] allSymbols = CardSymbol.values();
        
        for (int i = 0; i < 3; i++) {
            symbols.add(allSymbols[random.nextInt(allSymbols.length)]);
        }
        
        log.info("Símbolos sorteados: {}", symbols);
        return symbols;
    }
    
    /**
     * Calcula o resultado baseado nos símbolos
     */
    private GameResult calculateResult(List<CardSymbol> symbols, Double betAmount) {
        CardSymbol s1 = symbols.get(0);
        CardSymbol s2 = symbols.get(1);
        CardSymbol s3 = symbols.get(2);
        
        // Caso 1: Três símbolos iguais (JACKPOT!)
        if (s1 == s2 && s2 == s3) {
            double multiplier = s1.getMultiplier() * 2; // Dobra o multiplicador
            double winAmount = betAmount * multiplier;
            return new GameResult(
                true, 
                winAmount, 
                multiplier,
                "🎉 JACKPOT! Três " + s1.getName() + "!"
            );
        }
        
        // Caso 2: Dois símbolos iguais
        if (s1 == s2 || s2 == s3 || s1 == s3) {
            CardSymbol matchedSymbol = (s1 == s2) ? s1 : (s2 == s3) ? s2 : s1;
            double multiplier = matchedSymbol.getMultiplier();
            double winAmount = betAmount * multiplier;
            return new GameResult(
                true, 
                winAmount, 
                multiplier,
                "✨ Dois " + matchedSymbol.getName() + "! Você ganhou!"
            );
        }
        
        // Caso 3: Nenhum símbolo igual - PERDEU
        return new GameResult(
            false, 
            0.0, 
            0.0,
            "😔 Não foi dessa vez... Tente novamente!"
        );
    }
    
    /**
     * Classe auxiliar para armazenar resultado do jogo
     */
    private static class GameResult {
        boolean won;
        double winAmount;
        double multiplier;
        String message;
        
        GameResult(boolean won, double winAmount, double multiplier, String message) {
            this.won = won;
            this.winAmount = winAmount;
            this.multiplier = multiplier;
            this.message = message;
        }
    }
}