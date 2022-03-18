package yunhoSoccer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yunhoSoccer.domain.Player;
import yunhoSoccer.domain.Team;
import yunhoSoccer.domain.Trade;
import yunhoSoccer.domain.TradePlayer;
import yunhoSoccer.domain.PlayerStatus;
import yunhoSoccer.domain.Position;
import yunhoSoccer.domain.teamplayer.*;
import yunhoSoccer.repo.PlayerRepository;
import yunhoSoccer.repo.TeamPlayerRepository;
import yunhoSoccer.repo.TeamRepository;
import yunhoSoccer.repo.TradeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final TeamPlayerRepository teamPlayerRepository;

    /**
     * 선수 영입, 팀 선수 등록
     */
    @Transactional
    public Long makeTrade4recruit(Long teamId, Long playerId, Position position) {
        Team team = teamRepository.findById(teamId).get();
        Player player = playerRepository.findById(playerId).get();

        TradePlayer tradePlayer = new TradePlayer(player);
        Trade trade = Trade.createTradeOfRecruit(team, tradePlayer);

        switch (position) {
            case FORWARD -> {
                Forward forward = Forward.builder()
                                            .team(team)
                                            .basicInfo(player.getBasicInfo())
                                            .position(Position.FORWARD)
                                            .numberOfMatch(0)
                                            .goal(0)
                                            .assist(0)
                                            .playerId(player.getId())
                                            .shotAccuracy(0)
                                            .goalChance(0)
                                            .build();

                teamPlayerRepository.save(forward);
            }

            case MIDFIELDER -> {
                Midfielder midfielder = Midfielder.builder()
                                                    .team(team)
                                                    .basicInfo(player.getBasicInfo())
                                                    .position(Position.MIDFIELDER)
                                                    .numberOfMatch(0)
                                                    .goal(0)
                                                    .assist(0)
                                                    .playerId(player.getId())
                                                    .passRate(0)
                                                    .wonOfTackle(0)
                                                    .build();

                teamPlayerRepository.save(midfielder);
            }

            case DEFENDER -> {
                Defender defender = Defender.builder()
                                            .team(team)
                                            .basicInfo(player.getBasicInfo())
                                            .position(Position.DEFENDER)
                                            .numberOfMatch(0)
                                            .goal(0)
                                            .assist(0)
                                            .playerId(player.getId())
                                            .wonOfTackle(0)
                                            .blockShot(0)
                                            .build();

                teamPlayerRepository.save(defender);
            }

            default -> {
                Goalkeeper goalkeeper = Goalkeeper.builder()
                                                    .team(team)
                                                    .basicInfo(player.getBasicInfo())
                                                    .position(Position.GOALKEEPER)
                                                    .numberOfMatch(0)
                                                    .goal(0)
                                                    .assist(0)
                                                    .playerId(player.getId())
                                                    .cleanSheet(0)
                                                    .save(0)
                                                    .build();

                teamPlayerRepository.save(goalkeeper);
            }
        }

        tradeRepository.save(trade); //auto persist : tradePlayer

        return trade.getId();
    }

    /**
     * 선수 방출, 팀 선수 제외
     */
    @Transactional
    public Long makeTrade4release(Long teamId, Long teamPlayerId, int price) {
        Team team = teamRepository.findById(teamId).get();
        TeamPlayer teamPlayer = teamPlayerRepository.findById(teamPlayerId).get();
        Optional<Player> optionalPlayer = playerRepository.findById(teamPlayer.getPlayerId());

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();
            TradePlayer tradePlayer = new TradePlayer(player);
            Trade trade = Trade.createTradeOfRelease(team, price, tradePlayer);

            tradeRepository.save(trade); //auto persist : tradePlayer
            teamPlayerRepository.delete(teamPlayer);

            return trade.getId();

        } else {
            Player player = Player.builder()
                                    .basicInfo(teamPlayer.getBasicInfo())
                                    .price(price)
                                    .playerStatus(PlayerStatus.TRADED)
                                    .build();

            TradePlayer tradePlayer = new TradePlayer(player);
            Trade trade = Trade.createTradeOfRelease(team, price, tradePlayer);

            playerRepository.save(player);
            tradeRepository.save(trade); //auto persist : tradePlayer
            teamPlayerRepository.delete(teamPlayer);

            return trade.getId();
        }
    }

    public Trade findTrade(Long tradeId) {
        return tradeRepository.findById(tradeId)
                                .orElseThrow(() -> new IllegalStateException("Can't find that trade."));
    }

    public List<Trade> findTrades() {
        return tradeRepository.findAll();
    }
}
