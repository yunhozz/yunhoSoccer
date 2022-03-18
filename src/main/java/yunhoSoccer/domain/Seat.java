package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yunhoSoccer.domain.exception.NotEnoughSeatException;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

    private int premiumSeat;
    private int vipSeat;
    private int standardSeat;

    public Seat(int premiumSeat, int vipSeat, int standardSeat) {
        this.premiumSeat = premiumSeat;
        this.vipSeat = vipSeat;
        this.standardSeat = standardSeat;
    }

    public void addSeat(Grade grade, int count) {
        switch (grade) {
            case PREMIUM -> premiumSeat += count;
            case VIP -> vipSeat += count;
            default -> standardSeat += count;
        }
    }

    public void removeSeat(Grade grade, int count) {
        switch (grade) {
            case PREMIUM:
                if (premiumSeat - count >= 0) {
                    premiumSeat -= count;

                } else {
                    throw new NotEnoughSeatException("no more premium seat");
                }

                break;

            case VIP:
                if (vipSeat - count >= 0) {
                    vipSeat -= count;

                } else {
                    throw new NotEnoughSeatException("no more VIP seat");
                }

                break;

            case STANDARD:
                if (standardSeat - count >= 0) {
                    standardSeat -= count;

                } else {
                    throw new NotEnoughSeatException("no more standard seat");
                }
        }
    }

    public int getPriceByGrade(Grade grade) {
        int price;

        switch (grade) {
            case PREMIUM -> price = 100000;
            case VIP -> price = 70000;
            default -> price = 50000;
        }

        return price;
    }

    public int getTotalSeat() {
        return premiumSeat + vipSeat + standardSeat;
    }
}
