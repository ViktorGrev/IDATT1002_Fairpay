package no.ntnu.idatt1002.model.economy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The budget class maps multiple expense types to a number.
 */
public class Budget {

  private final Map<ExpenseType, BigDecimal> budget = new HashMap<>();

  /**
   * Enter a specified amount for an expense type for the budget.
   *
   * @param   type the expense type
   * @param   amount the amount of the expense type
   * @throws  IllegalArgumentException if the type is null or the amount is negative
   */
  public void add(ExpenseType type, BigDecimal amount) {
    if (type == null) throw new IllegalArgumentException("type is null");
    if (amount == null) throw new IllegalArgumentException("amount is null");
    if (amount.compareTo(BigDecimal.ZERO) < 0)
      throw new IllegalArgumentException("amount is negative");
    budget.put(type, amount);
  }

  /**
   * Returns the amount for the specified expense type in the budget.
   *
   * @param   type the expense type
   * @return  the amount for the specified expense type
   * @throws  IllegalArgumentException if the expense type is null
   */
  public BigDecimal getAmount(ExpenseType type) {
    if (type == null) throw new IllegalArgumentException("type is null");
    return budget.getOrDefault(type, BigDecimal.ZERO);
  }

  /**
   * Returns the total amount spendable for the budget.
   *
   * @return  the total amount spendable for the budget
   */
  public BigDecimal getTotal() {
    BigDecimal total = BigDecimal.ZERO;
    for (ExpenseType expense : ExpenseType.values()) {
      total = total.add(getAmount(expense));
    }
    return total;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Budget budget1 = (Budget) o;
    return Objects.equals(budget, budget1.budget);
  }

  @Override
  public int hashCode() {
    return Objects.hash(budget);
  }
}
