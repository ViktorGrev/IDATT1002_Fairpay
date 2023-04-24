package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.economy.Income;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This interface defines methods to interact with incomes and income related tasks.
 */
public interface IncomeDAO extends DAO<Income, Long> {

    /**
     * Creates a new income.
     * @param   userId the ID of the user adding the income
     * @param   name the name of the income
     * @param   amount the amount
     * @param   date the date
     * @param   shares the amount of people this income is shared between
     * @return  a new income
     */
    Income create(long userId, String name, BigDecimal amount, Date date, int shares);

    /**
     * Deletes an income.
     * @param   incomeId the income ID
     */
    void delete(long incomeId);
}
