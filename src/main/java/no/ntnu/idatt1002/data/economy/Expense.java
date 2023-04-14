package no.ntnu.idatt1002.data.economy;

import java.math.BigDecimal;
import java.util.Date;

/**
    The Expense class represents an expense made by a user in a financial system.
    It contains information about the user who made the expense, the type of expense,
    the name of the expense, the amount of the expense, and the date it was made.
 */
public class Expense {

    private final long expenseId; // The ID of the expense
    private final long userId; // The ID of the user who made the expense
    private final ExpenseType type; // The type of the expense
    private final String name; // The name of the expense
    private final BigDecimal amount; // The amount of the expense
    private final Date date; // The date the expense was made

    /**
     * Creates a new Expense object with the given expense type, amount, and user ID.
     * The name of the expense is set to "No description".
     *
     * @param type the type of the expense
     * @param amount the amount of the expense
     * @param userId the ID of the user who made the expense
     */
    public Expense(long expenseId, ExpenseType type, BigDecimal amount, long userId) {
        this(expenseId, userId, type, null, amount, new Date());
    }

    /**
     * Creates a new Expense object with the given user ID, expense type, name, and amount.
     * The date of the expense is set to the current date.
     *
     * @param expenseId
     * @param userId    the ID of the user who made the expense
     * @param type      the type of the expense
     * @param name      the name of the expense
     * @param amount    the amount of the expense
     * @throws IllegalArgumentException if the user ID is negative, the expense type is null,
     *                                  the name is blank, or the amount is negative
     */
    public Expense(long expenseId, long userId, ExpenseType type, String name, BigDecimal amount, Date date) {
        this.expenseId = expenseId;
        if (userId < 0){
            throw new IllegalArgumentException("Incorrect user-id");
        }
        if (type == null){
            throw new IllegalArgumentException("The expense type can't be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("The amount cannot be a negative integer");
        }
        this.userId = userId;
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Returns the ID of the expense.
     * @return  the ID of the expense
     */
    public long getExpenseId() {
        return expenseId;
    }

    /**
     * Returns the ID of the user who made the expense.
     *
     * @return the ID of the user who made the expense
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Returns the type of the expense.
     *
     * @return the type of the expense
     */
    public ExpenseType getType() {
        return type;
    }

    /**
     * Returns the name of the expense.
     *
     * @return the name of the expense
     */
    public String getName() {
        return name;
    }

    /**
     * Returns true if the expense has a name, false otherwise.
     *
     * @return true if the expense has a name, false otherwise
     */
    public boolean hasName() {
        return name != null;
    }

    /**
     * Returns the amount of the expense.
     *
     * @return the amount of the expense
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the date the expense was made.
     *
     * @return the date the expense was made
     */
    public Date getDate() {
        return date;
    }
}
