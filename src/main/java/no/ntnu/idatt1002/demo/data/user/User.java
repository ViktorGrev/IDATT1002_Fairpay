package no.ntnu.idatt1002.demo.data.user;

import no.ntnu.idatt1002.demo.data.Budget;

import java.util.Date;

public final class User {

    private final int id;
    private final String username;
    private final String password;
    private final Date registerDate;
    private final long phoneNumber;
    private final Budget budget;

    public User(int id, String username, String password, Date registerDate, long phoneNumber, Budget budget) {
        if(username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be null or blank");
        if(password == null || password.isBlank())
            throw new IllegalArgumentException("Password cannot be null or blank");
        if(registerDate == null)
            throw new IllegalArgumentException("Register date cannot be null");
        if(phoneNumber < 0)
            throw new IllegalArgumentException("Phone number cannot be negative");
        if(budget == null)
            throw new IllegalArgumentException("Budget cannot be null");
        this.id = id;
        this.username = username;
        this.password = password;
        this.registerDate = registerDate;
        this.phoneNumber = phoneNumber;
        this.budget = budget;
    }

    public int getId() {
        return id;
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