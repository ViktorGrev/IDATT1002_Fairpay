package no.ntnu.idatt1002.data;

import no.ntnu.idatt1002.data.economy.Budget;

import java.util.Date;

/**
 * A class representing a user of a certain system.
 */
public final class User {

    private final long id; // The unique id of the user
    private final String username; // The username of the user
    private final String password; // The password of the user
    private final Date registerDate; // The date when the user registered
    private final long phoneNumber; // The phone number of the user
    private final Budget budget; // The budget of the user

    /**
     * Constructs a new User object with the specified id, username, password, register date, phone number and budget.
     *
     * @param id the unique id of the user
     * @param username the username of the user. Cannot be null or blank.
     * @param password the password of the user. Cannot be null or blank.
     * @param registerDate the date when the user registered. Cannot be null.
     * @param phoneNumber the phone number of the user. Cannot be negative.
     * @param budget the budget of the user. Cannot be null.
     * @throws IllegalArgumentException if the username is null or blank, the password is null or blank,
     * the register date is null, the phone number is negative or the budget is null.
     */
    public User(long id, String username, String password, Date registerDate, long phoneNumber, Budget budget) {
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

    /**
     * Returns the unique id of the user.
     *
     * @return the unique id of the user.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the date when the user registered.
     *
     * @return the date when the user registered.
     */
    public Date getRegisterDate() {
        return registerDate;
    }

    /**
     * Returns the phone number of the user.
     *
     * @return the phone number of the user.
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the budget of the user.
     *
     * @return the budget of the user.
     */
    public Budget getBudget() {
        return budget;
    }
}