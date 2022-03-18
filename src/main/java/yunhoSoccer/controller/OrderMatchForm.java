package yunhoSoccer.controller;

import lombok.Getter;
import lombok.Setter;
import yunhoSoccer.domain.Match;
import yunhoSoccer.domain.Grade;

@Getter
@Setter
public class OrderMatchForm {

    private Match match;
    private Grade grade;
    private int count;
}
