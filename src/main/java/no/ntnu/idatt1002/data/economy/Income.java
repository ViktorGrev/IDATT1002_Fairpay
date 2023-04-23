package no.ntnu.idatt1002.data.economy;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The Income class represents an income with an amount, a type, a user ID, and a date.
 */
public class Income {


  private final long incomeId; // The ID of the income
  private final long userId; // The user ID associated with the income
  private final Date addDate; // The date when the expense was added
  private final String name; // The name of the income
  private final BigDecimal amount; // The amount of the income
  private final Date date; // The date the income was added
  private final int shares;

  /**
   * Constructs an Income object with the specified amount, type, user ID, and date.
   *
   * @param type     the type of the income
   * @param incomeId
   * @param amount   the amount of the income
   * @param userId   the user ID associated with the income
   * @param addDate
   * @param date     the date the income was received
   * @param shares
   * @throws IllegalArgumentException if the user ID is negative, the income type is null, the date is null, or the amount is negative
   */
  public Income(long incomeId, BigDecimal amount, String name, long userId, Date addDate, Date date, int shares) {
    this.incomeId = incomeId;
    this.addDate = addDate;
    this.shares = shares;
    if (userId < 0){
      throw new IllegalArgumentException("Incorrect income-id");
    }
    if (name == null){
      throw new IllegalArgumentException("The income name can't be null");
    }
    if (date == null){
      throw new IllegalArgumentException("The date can't be null");
    }
    if (amount.compareTo(BigDecimal.ZERO) < 0){
      throw new IllegalArgumentException("The amount cannot be a negative integer");
    }
    this.amount = amount;
    this.name = name;
    this.userId = userId;
    this.date = date;
  }

  /**
   * Returns the ID of the income.
   * @return  the ID of the income
   */
  public long getIncomeId() {
    return incomeId;
  }

  /**
   * Returns the ID of the user who added the income.
   * @return  the ID of the user who added the income
   */
  public long getUserId() {
    return userId;
  }

  /**
   * Returns the date when the income was added.
   * @return  the date when the income was added
   */
  public Date getAddDate() {
    return addDate;
  }

  /**
   * Returns the name of the income.
   * @return the name of the income
   */
  public String getName(){
    return name;
  }

  /**
   * Returns the amount of the income.
   * @return the amount of the income
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Returns the date the income was added.
   * @return the date the income was added
   */
  public Date getDate() {
    return date;
  }

  public int getShares() {
    return shares;
  }
}
