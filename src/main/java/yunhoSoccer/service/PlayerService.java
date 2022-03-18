package yunhoSoccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Player;
import yunhoSoccer.dto.PlayerDto;
import yunhoSoccer.repo.PlayerRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional
    public Long makePlayer(PlayerDto playerDto) {
        Player player = playerRepository.save(playerDto.toEntity());
        return player.getId();
    }

    public Optional<Player> findPlayer(Long playerId) {
        return playerRepository.findById(playerId);
    }

    public List<Player> findPlayers() {
        return playerRepository.findAll();
    }
}
