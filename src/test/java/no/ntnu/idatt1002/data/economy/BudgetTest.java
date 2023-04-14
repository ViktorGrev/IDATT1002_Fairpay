package no.ntnu.idatt1002.data.economy;

import no.ntnu.idatt1002.data.economy.Budget;
import no.ntnu.idatt1002.data.economy.ExpenseType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

  @Test
  void add() {
    Budget budget = new Budget();

    budget.add(ExpenseType.INTERNET, BigDecimal.valueOf(123));
    assertEquals(BigDecimal.valueOf(123),budget.getAmount(ExpenseType.INTERNET));
  }
}