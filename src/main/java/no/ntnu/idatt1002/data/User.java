package no.ntnu.idatt1002.data;

import java.util.Date;

/**
 * This class represents a user of the application.
 */
public final class User {

  public static long CURRENT = -1; // The current user ID

  private final long id; // The unique id of the user
  private String username; // The username of the user
  private final String password; // The encrypted password of the user
  private final Date registerDate; // The date when the user was registered
  private final Date lastLogin; // The date of the last login
  private long phoneNumber; // The phone number of the user

  /**
   * Constructs a user object with the specified values.
   *
   * @param   id the unique id of the user
   * @param   username the username of the user. Cannot be null or blank.
   * @param   password the password of the user. Cannot be null or blank.
   * @param   registerDate the date when the user registered. Cannot be null.
   * @param   phoneNumber the phone number of the user. Cannot be negative.
   * @throws  IllegalArgumentException if the username is null or blank, the password is null or blank,
   * the register date is null, the phone number is negative or the budget is null.
   */
  public User(long id, String username, String password, Date registerDate, Date lastLogin, long phoneNumber) {
    if(username == null || username.isBlank())
      throw new IllegalArgumentException("Username cannot be null or blank");
    if(password == null || password.isBlank())
      throw new IllegalArgumentException("Password cannot be null or blank");
    if(registerDate == null)
      throw new IllegalArgumentException("Register date cannot be null");
    if(lastLogin == null)
      throw new IllegalArgumentException("Last login cannot be null");
    if(phoneNumber < 0)
      throw new IllegalArgumentException("Phone number cannot be negative");
    this.id = id;
    this.username = username;
    this.password = password;
    this.registerDate = registerDate;
    this.lastLogin = lastLogin;
    this.phoneNumber = phoneNumber;
  }

  /**
   * Returns the user ID
   *
   * @return  the user ID
   */
  public long getId() {
    return id;
  }

  /**
   * Set the username of the user.
   *
   * @param   username the username
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Returns the username of the user.
   *
   * @return  the username of the user
   */
  public String getUsername() {
    return username;
  }

  /**
   * Returns the encrypted password of the user.
   *
   * @return  the password of the user
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns the date when the user was registered.
   *
   * @return  the date when the user was registered
   */
  public Date getRegisterDate() {
    return registerDate;
  }

  /**
   * Returns the date when the user was last logged in.
   *
   * @return  the last login date
   */
  public Date getLastLogin() {
    return lastLogin;
  }

  /**
   * Set the phone number.
   *
   * @param   phoneNumber the phone number
   */
  public void setPhoneNumber(long phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * Returns the phone number of the user.
   *
   * @return  the phone number of the user.
   */
  public long getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Set the current user
   *
   * @param   userId the user ID
   */
  public static void setCurrent(long userId) {
    CURRENT = userId;
  }
}