package yunhoSoccer.dto;

import lombok.Data;
import yunhoSoccer.domain.BasicInfo;
import yunhoSoccer.domain.Player;
import yunhoSoccer.domain.MainFoot;
import yunhoSoccer.domain.PlayerStatus;

@Data
public class PlayerDto {

    private String name;
    private int age;
    private double height;
    private double weight;
    private MainFoot mainFoot;
    private int price;
    private PlayerStatus playerStatus;

    public Player toEntity() {
        return Player.builder()
                        .basicInfo(new BasicInfo(name, age, height, weight, mainFoot))
                        .price(price)
                        .playerStatus(playerStatus)
                        .build();
    }
}
