package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BasicInfo {

    private String name;
    private int age;
    private double height;
    private double weight;

    @Enumerated(EnumType.STRING)
    private MainFoot mainFoot; //RIGHT, LEFT, BOTH

    public BasicInfo(String name, int age, double height, double weight, MainFoot mainFoot) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.mainFoot = mainFoot;
    }
}
