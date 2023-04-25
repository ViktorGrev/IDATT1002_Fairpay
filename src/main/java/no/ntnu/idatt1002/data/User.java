package no.ntnu.idatt1002.data;

import java.util.Date;
import java.util.Objects;

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
    validateUsername(username);
    if (password == null || password.isBlank())
      throw new IllegalArgumentException("Password cannot be null or blank");
    if (registerDate == null)
      throw new IllegalArgumentException("Register date cannot be null");
    if (lastLogin == null)
      throw new IllegalArgumentException("Last login cannot be null");
    validatePhoneNumber(phoneNumber);
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
    validateUsername(username);
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
   * Checks if the specified username is valid.
   *
   * @param   username the username
   * @throws  IllegalArgumentException if the username is null,
   *          blank or outside length bounds
   */
  public static void validateUsername(String username) {
    if(username == null)
      throw new IllegalArgumentException("username is null");
    if(username.isBlank())
      throw new IllegalArgumentException("username is blank");
    if(username.length() < 3 || username.length() > 16)
      throw new IllegalArgumentException("username must be between 3-16 characters");
    if(username.contains(" "))
      throw new IllegalArgumentException("username cannot contain spaces");
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
    validatePhoneNumber(phoneNumber);
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
   * Checks if the specified number has 8 digits.
   *
   * @param   phoneNumber the number
   * @throws  IllegalArgumentException if the phone number
   *          does not have 8 digits
   */
  public static void validatePhoneNumber(long phoneNumber) {
    if(phoneNumber < 10000000 || phoneNumber > 99999999)
      throw new IllegalArgumentException("phone number is invalid");
  }

  /**
   * Set the current user
   *
   * @param   userId the user ID
   */
  public static void setCurrent(long userId) {
    CURRENT = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id == user.id && phoneNumber == user.phoneNumber && Objects.equals(username, user.username)
            && Objects.equals(password, user.password) && Objects.equals(registerDate, user.registerDate)
            && Objects.equals(lastLogin, user.lastLogin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, password, registerDate, lastLogin, phoneNumber);
  }
}