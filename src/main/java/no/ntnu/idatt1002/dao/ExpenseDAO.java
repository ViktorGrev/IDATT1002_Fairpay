package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.ExpenseType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This interface defines methods to interact with expenses and expense related tasks.
 */
public interface ExpenseDAO extends DAO<Expense, Long> {

    /**
     * Creates an expense.
     * @param   userId the ID of the user adding the expense
     * @param   type the expense type
     * @param   name the name
     * @param   amount the amount
     * @param   date the date
     * @param   shares the amount of people this expense is shared between
     * @return  a new expense
     */
    Expense create(long userId, ExpenseType type, String name, BigDecimal amount, Date date, int shares);
}
