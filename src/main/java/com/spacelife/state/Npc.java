package com.spacelife.state;

import java.time.LocalDate;

public class Npc extends Person {
    public Npc(Name name, LocalDate birthday, Gender gender) {
        super(name, birthday, gender);
    }
}
