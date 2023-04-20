package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.economy.Income;

import java.math.BigDecimal;
import java.util.Date;

public interface IncomeDAO extends DAO<Income, Long> {

    Income create(long userId, String name, BigDecimal amount, Date date, int shares);
}
