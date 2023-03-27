package no.ntnu.idatt1002.data.economy;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The Income class represents an income with an amount, a type, a user ID, and a date.
 */
public class Income {

  //The amount of the income.
  private final BigDecimal Amount;

  //The type of the income.
  private final String type;

  //The user ID associated with the income.
  private final long userId;

  //The date the income was received.
  private final Date date;

  /**
   * Constructs an Income object with the specified amount, type, user ID, and date.
   *
   * @param amount the amount of the income
   * @param type the type of the income
   * @param userId the user ID associated with the income
   * @param date the date the income was received
   * @throws IllegalArgumentException if the user ID is negative, the income type is null, the date is null, or the amount is negative
   */
  public Income(BigDecimal amount, String type, long userId, Date date) {
    if (userId < 0){
      throw new IllegalArgumentException("Incorrect user-id");
    }
    if (type == null){
      throw new IllegalArgumentException("The income type can't be null");
    }
    if (date == null){
      throw new IllegalArgumentException("The date can't be null");
    }
    if (amount.compareTo(BigDecimal.ZERO) < 0){
      throw new IllegalArgumentException("The amount cannot be a negative integer");
    }
    this.Amount = amount;
    this.type = type;
    this.userId = userId;
    this.date = date;
  }

  /**
   * Returns the amount of the income.
   *
   * @return the amount of the income
   */
  public BigDecimal getAmount() {
    return Amount;
  }

  /**
   * Returns the type of the income.
   *
   * @return the type of the income
   */
  public String getIncomeType(){
    return type;
  }

  /**
   * Returns the user ID associated with the income.
   *
   * @return the user ID associated with the income
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Returns the date the income was received.
   *
   * @return the date the income was received
   */
  public Date getDate() {
    return date;
  }
}
