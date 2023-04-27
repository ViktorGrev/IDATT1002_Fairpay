package no.ntnu.idatt1002.model.economy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * The Income class represents an income with an amount, a type, a user ID, and a date.
 */
public class Income {

  private final long id; // The ID of the income
  private final long userId; // The user ID associated with the income
  private final Date addDate; // The date when the expense was added
  private final String name; // The name of the income
  private final BigDecimal amount; // The amount of the income
  private final Date date; // The date the income was added
  private final int shares; // The amount of people this income is shared between

  /**
   * Constructs an income object with the specified values.
   *
   * @param id      the ID of the income
   * @param userId  the user ID associated with the income
   * @param amount  the amount of the income
   * @param name    the income name
   * @param addDate the date the expense was added
   * @param date    the date the income was received
   * @param shares  the amount of people the expense is shared between
   * @throws IllegalArgumentException if any of the following are true:
   *  <ul>
   *    <li>id is invalid</li>
   *    <li>userId is invalid</li>
   *    <li>addDate is null</li>
   *    <li>income name is null</li>
   *    <li>amount is negative</li>
   *    <li>date is null</li>
   *    <li>shares is negative</li>
   *  </ul>
   */
  public Income(long id, long userId, BigDecimal amount, String name, Date addDate,
                Date date, int shares) {
    if (id < 0)
      throw new IllegalArgumentException("invalid ID");
    if (userId < 0)
      throw new IllegalArgumentException("invalid user ID");
    if (amount == null)
      throw new IllegalArgumentException("amount is null");
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("amount is negative");
    if (name == null)
      throw new IllegalArgumentException("name is null");
    if (addDate == null)
      throw new IllegalArgumentException("addDate is null");
    if (date == null)
      throw new IllegalArgumentException("date is null");
    this.id = id;
    this.addDate = addDate;
    this.shares = shares;
    this.amount = amount;
    this.name = name;
    this.userId = userId;
    this.date = date;
  }

  /**
   * Returns the income ID.
   *
   * @return  the income ID
   */
  public long getId() {
    return id;
  }

  /**
   * Returns the ID of the user who added the income.
   *
   * @return  the ID of the user who added the income
   */
  public long getUserId() {
    return userId;
  }

  /**
   * Returns the date when the income was added.
   *
   * @return  the date when the income was added
   */
  public Date getAddDate() {
    return addDate;
  }

  /**
   * Returns the name of the income.
   *
   * @return  the name of the income
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the amount for the income.
   *
   * @return  the amount for the income
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Returns the date the income was added.
   *
   * @return  the date the income was added
   */
  public Date getDate() {
    return date;
  }

  /**
   * Returns the amount of people this income is shared between.
   *
   * @return  the amount of people this income is shared between
   */
  public int getShares() {
    return shares;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Income income = (Income) o;
    return id == income.id && userId == income.userId && shares == income.shares
            && Objects.equals(addDate, income.addDate) && Objects.equals(name, income.name)
            && Objects.equals(amount, income.amount) && Objects.equals(date, income.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, addDate, name, amount, date, shares);
  }
}
