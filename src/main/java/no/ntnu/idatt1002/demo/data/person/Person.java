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
        if(username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be null or empty");
        if(password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be null or empty");
        if(registerDate == null)
            throw new IllegalArgumentException("Register date cannot be null");
        if(phoneNumber < 0)
            throw new IllegalArgumentException("Phone number cannot be negative");
        if(budget == null)
            throw new IllegalArgumentException("Budget cannot be null");
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