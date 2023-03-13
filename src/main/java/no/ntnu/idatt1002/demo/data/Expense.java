package no.ntnu.idatt1002.demo.data;

import java.math.BigDecimal;
import java.util.Date;

public class Expense {

    private final long userId;
    private final ExpenseType type;
    private final String name;
    private final BigDecimal amount;
    private final Date date;

    public Expense(ExpenseType type, BigDecimal amount, long userId) {
        this(userId, type, null, amount);
    }

    public Expense(long userId, ExpenseType type, String name, BigDecimal amount) {
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
