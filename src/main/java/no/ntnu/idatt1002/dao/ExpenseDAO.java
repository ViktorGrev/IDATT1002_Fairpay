package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.ExpenseType;

import java.math.BigDecimal;
import java.util.Date;

public interface ExpenseDAO extends DAO<Expense, Long> {

    Expense create(long userId, ExpenseType type, String name, BigDecimal amount, Date date);
}
