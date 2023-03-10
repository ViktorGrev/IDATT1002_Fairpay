package no.ntnu.idatt1002.demo.data.person;

import no.ntnu.idatt1002.demo.data.Budget;

import java.util.Date;

public final class Person {

    private final String username;
    private final String password;
    private final Date registerDate;
    private final long phoneNumber;
    private final Budget budget;

    private Person(String username, String password, Date registerDate, long phoneNumber, Budget budget) {
        this.username = username;
        this.password = password;
        this.registerDate = registerDate;
        this.phoneNumber = phoneNumber;
        this.budget = budget;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public Budget getBudget() {
        return budget;
    }
}