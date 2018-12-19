package com.spacelife.state;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Person implements Hoverable {
    private final Name name;
    private final LocalDate birthday;
    private final Gender gender;

    public Person(Name name, LocalDate birthday, Gender gender) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    public Name getName() {
        return name;
    }

    @Override
    public String getText() {
        return name.getFirstName();
    }

    @Override
    public String getExpandedText() {
        return "Name: " + name.getFullName() + "\nBirthday: " + birthday.format(DateTimeFormatter.ISO_DATE) + "\nGender: " + gender;
    }
}
