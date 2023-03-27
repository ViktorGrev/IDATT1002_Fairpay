package no.ntnu.idatt1002.data.economy;

import java.math.BigDecimal;
import java.util.Date;

public class Expense {

    private final long userId;
    private final ExpenseType type;
    private final String name;
    private final BigDecimal amount;
    private final Date date;

    public Expense(ExpenseType type, BigDecimal amount, long userId) {
        this(userId, type, "No description", amount);
    }

    public Expense(long userId, ExpenseType type, String name, BigDecimal amount) {
        if (userId < 0){
            throw new IllegalArgumentException("Incorrect user-id");
        }
        if (type == null){
            throw new IllegalArgumentException("The expense type can't be null");
        }
        if (name.isBlank()){
            throw new IllegalArgumentException("The name can't be blank");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("The amount cannot be a negative integer");
        }
        this.userId = userId;
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.date = new Date();
    }

    public long getUserId() {
        return userId;
    }

    public ExpenseType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean hasName() {
        return name != null;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }
}
