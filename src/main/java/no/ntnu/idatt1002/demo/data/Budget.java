package no.ntnu.idatt1002.demo.data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Budget {

    private BigDecimal total;
    private final Map<ExpenseType, BigDecimal> budget = new HashMap<>();

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void add(ExpenseType type, BigDecimal amount) {
        budget.put(type, amount);
    }

    public BigDecimal getAmount(ExpenseType type) {
        return budget.get(type);
    }
}
