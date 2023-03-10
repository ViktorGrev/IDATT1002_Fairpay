package no.ntnu.idatt1002.demo.data;

import java.util.HashMap;
import java.util.Map;

public class Budget {

    private final Map<ExpenseType, Long> budget = new HashMap<>();

    public void add(ExpenseType type, long amount) {
        budget.put(type, amount);
    }

    public long getAmount(ExpenseType type) {
        return budget.get(type);
    }
}
