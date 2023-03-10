package no.ntnu.idatt1002.demo.data;

import java.math.BigDecimal;
import java.util.Date;

public class Expense {

    private final ExpenseType type;
    private final String name;
    private final BigDecimal amount;
    private final Date date;

    public Expense(ExpenseType type, BigDecimal amount) {
        this(type, null, amount);
    }

    public Expense(ExpenseType type, String name, BigDecimal amount) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.date = new Date();
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

    public static class Builder {

        private ExpenseType type;
        private String name;
        private BigDecimal amount;

        public void type(ExpenseType type) {
            this.type = type;
        }

        public void name(String name) {
            this.name = name;
        }

        public void amount(BigDecimal amount) {
            this.amount = amount;
        }

        public Expense build() {
            return new Expense(type, name, amount);
        }
    }
}
