package com.heitor.duatcassinos2_back.service;

import com.heitor.duatcassinos2_back.dto.request.MoonGameRequest;
import com.heitor.duatcassinos2_back.dto.response.MoonGameResponse;
import com.heitor.duatcassinos2_back.model.Player;
import com.heitor.duatcassinos2_back.model.enums.MoonPhase;
import com.heitor.duatcassinos2_back.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MoonGameService {

    private final PlayerRepository playerRepository;
    private final Random random = new Random();

    @Transactional
    public MoonGameResponse play(MoonGameRequest request) {

        Player player = playerRepository.findById(request.getPlayerId())
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado"));

        if (player.getBalance() < request.getBetAmount()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        MoonPhase finalPhase = drawMoonPhase();
        MoonPhase chosenPhase = request.getChosenPhase();

        boolean won = finalPhase == chosenPhase;
        double amountChange;

        if (won) {
            amountChange = request.getBetAmount() * chosenPhase.getWinMultiplier();
            player.addBalance(amountChange);
            player.incrementWins();
        } else {
            amountChange = request.getBetAmount() * chosenPhase.getLossMultiplier();
            player.subtractBalance(amountChange);
            player.incrementLosses();
        }

        playerRepository.save(player);

        return MoonGameResponse.builder()
                .playerId(player.getId())
                .chosenPhase(chosenPhase)
                .finalPhase(finalPhase)
                .won(won)
                .amountChange(amountChange)
                .newBalance(player.getBalance())
                .message(buildMessage(won, chosenPhase, finalPhase, amountChange))
                .totalGames(player.getTotalGames())
                .wins(player.getWins())
                .losses(player.getLosses())
                .winRate(player.getWinRate())
                .build();
    }

    private MoonPhase drawMoonPhase() {
        double roll = random.nextDouble();
        double cumulative = 0;

        for (MoonPhase phase : MoonPhase.values()) {
            cumulative += phase.getProbability();
            if (roll <= cumulative) {
                return phase;
            }
        }

        return MoonPhase.FULL_MOON; // fallback
    }

    private String buildMessage(boolean won, MoonPhase chosen, MoonPhase finalPhase, double value) {
        if (won) {
            return String.format(
                    "%s %s! Você acertou e ganhou R$ %.2f",
                    finalPhase.getEmoji(),
                    finalPhase.getDisplayName(),
                    value
            );
        }
        return String.format(
                "%s %s! Você errou e perdeu R$ %.2f",
                finalPhase.getEmoji(),
                finalPhase.getDisplayName(),
                value
        );
    }
}
