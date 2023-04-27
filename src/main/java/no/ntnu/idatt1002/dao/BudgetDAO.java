package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.model.economy.Budget;
import no.ntnu.idatt1002.model.economy.ExpenseType;

import java.math.BigDecimal;

/**
 * This interface defines methods to interact with budgets and budget related tasks.
 */
public interface BudgetDAO extends DAO<Budget, Long> {

  /**
   * Set an amount for an expense type within a budget for a group.
   *
   * @param   groupId the group ID
   * @param   type the expense type
   * @param   amount the amount
   */
  void addType(long groupId, ExpenseType type, BigDecimal amount);
}
