package com.spacelife.state;

import java.time.LocalDate;

public class Player extends Person {
    public Player(Name name, LocalDate birthday, Gender gender) {
        super(name, birthday, gender);
    }
}
