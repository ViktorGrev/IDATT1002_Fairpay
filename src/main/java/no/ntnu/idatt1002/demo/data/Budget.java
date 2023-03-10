package no.ntnu.idatt1002.demo.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Budget {

    private BigDecimal total;
    private final Map<ExpenseType, BigDecimal> budget = new HashMap<>();

    public void setTotal(BigDecimal total) {
        if(total.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total cannot be negative");
        }
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void add(ExpenseType type, BigDecimal amount) {
        if(amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if(type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        budget.put(type, amount);
    }

    public BigDecimal getAmount(ExpenseType type) {
        if(type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        return budget.get(type);
    }
}
