package com.heitor.duatcassinos2_back.repository;

import com.heitor.duatcassinos2_back.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para gerenciar jogadores
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    
    /**
     * Busca jogador por nickname
     */
    Optional<Player> findByNickname(String nickname);
    
    /**
     * Verifica se existe jogador com esse nickname
     */
    boolean existsByNickname(String nickname);
}
