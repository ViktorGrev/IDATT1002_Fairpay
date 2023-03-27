package no.ntnu.idatt1002.data.economy;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Budget {

    //representing the total amount of the budget
    private BigDecimal total;

    //maps ExpenseType objects to their respective amounts.
    private final Map<ExpenseType, BigDecimal> budget = new HashMap<>();

    /**
     * Sets the total amount of the budget.
     *
     * @param total the total amount of the budget
     * @throws IllegalArgumentException if the total amount is negative
     */
    public void setTotal(BigDecimal total) {
        if(total.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total cannot be negative");
        }
        this.total = total;
    }

    /**
     * Gets the total amount of the budget.
     *
     * @return the total amount of the budget
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Adds an expense to the budget.
     *
     * @param type the type of the expense
     * @param amount the amount of the expense
     * @throws IllegalArgumentException if the amount is negative or if the type is null
     */
    public void add(ExpenseType type, BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if(type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        budget.put(type, amount);
    }

    /**
     * Gets the amount of an expense type in the budget.
     *
     * @param type the type of the expense
     * @return the amount of the expense
     * @throws IllegalArgumentException if the type is null
     */
    public BigDecimal getAmount(ExpenseType type) {
        if(type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        return budget.get(type);
    }
}
