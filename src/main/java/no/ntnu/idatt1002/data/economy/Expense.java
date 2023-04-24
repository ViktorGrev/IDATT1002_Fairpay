package no.ntnu.idatt1002.data.economy;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The Expense class represents an expense made by a user in a financial system.
 * It contains information about the user who made the expense, the type of expense,
 * the name of the expense, the amount of the expense, and the date it was made.
 */
public class Expense {

  private final long id; // The ID of the expense
  private final long userId; // The ID of the user who made the expense
  private final Date addDate; // The date when the expense was added
  private final ExpenseType type; // The type of the expense
  private final String name; // The name of the expense
  private final BigDecimal amount; // The amount of the expense
  private final Date date; // The date the expense is for
  private final int shares; // The amount of people this expense is shared between

  /**
   * Constructs an expense with the specified values.
   *
   * @param id      the ID of the expense
   * @param userId  the ID of the user who made the expense
   * @param addDate the date the expense was added
   * @param type    the type of the expense
   * @param name    the name of the expense
   * @param amount  the amount of the expense
   * @param shares  the amount of people the expense is shared between
   * @throws IllegalArgumentException if any of the following are true:
   *  <ul>
   *     <li>id is invalid</li>
   *     <li>userId is invalid</li>
   *     <li>addDate is null</li>
   *     <li>expense type is null</li>
   *     <li>amount is negative</li>
   *     <li>date is null</li>
   *     <li>shares is negative</li>
   *  </ul>
   */
  public Expense(long id, long userId, Date addDate, ExpenseType type,
                 String name, BigDecimal amount, Date date, int shares) {
    if (id < 0)
      throw new IllegalArgumentException("invalid ID");
    if (userId < 0)
      throw new IllegalArgumentException("invalid user ID");
    if (addDate == null)
      throw new IllegalArgumentException("addDate is null");
    if (type == null)
      throw new IllegalArgumentException("expense type is null");
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("amount is negative");
    if (date == null)
      throw new IllegalArgumentException("date is null");
    if (shares < 0)
      throw new IllegalArgumentException("shares is negative");
    this.id = id;
    this.addDate = addDate;
    this.shares = shares;
    this.userId = userId;
    this.type = type;
    this.name = name;
    this.amount = amount;
    this.date = date;
  }

  /**
   * Returns the expense ID.
   *
   * @return  the expense ID
   */
  public long getId() {
    return id;
  }

  /**
   * Returns the user ID.
   *
   * @return  the user ID
   */
  public long getUserId() {
    return userId;
  }

  /**
   * Returns the date when the expense was added.
   *
   * @return  the date when the expense was added
   */
  public Date getAddDate() {
    return addDate;
  }

  /**
   * Returns the type of the expense.
   *
   * @return  the type of the expense
   */
  public ExpenseType getType() {
    return type;
  }

  /**
   * Returns the name of the expense.
   *
   * @return  the name of the expense
   */
  public String getName() {
    return name;
  }

  /**
   * Returns true if the expense has a name, false otherwise.
   *
   * @return  true if the expense has a name, false otherwise
   */
  public boolean hasName() {
    return name != null;
  }

  /**
   * Returns the amount of the expense.
   *
   * @return  the amount of the expense
   */
  public BigDecimal getAmount() {
    return amount;
  }

  /**
   * Returns the date the expense was made.
   *
   * @return  the date the expense was made
   */
  public Date getDate() {
    return date;
  }

  /**
   * Returns the amount of people this expense is shared between.
   *
   * @return  the amount of people this expense is shared between
   */
  public int getShares() {
    return shares;
  }
}
