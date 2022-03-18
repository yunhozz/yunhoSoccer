package yunhoSoccer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    private int accountNumber;
    private int money;

    public Account(int accountNumber, int money) {
        this.accountNumber = accountNumber;
        this.money = money;
    }

    public void deposit(int money) {
        this.money += money;
    }

    public void withdrawal(int money) {
        int remainMoney = this.money - money;

        if (remainMoney >= 0) {
            this.money = remainMoney;

        } else {
            throw new IllegalStateException("no more money");
        }
    }
}
