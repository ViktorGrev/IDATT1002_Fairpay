package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.economy.Budget;
import no.ntnu.idatt1002.data.economy.ExpenseType;

import java.math.BigDecimal;

public interface BudgetDAO extends DAO<Budget, Long> {

    void addType(long groupId, ExpenseType type, BigDecimal amount);
}
