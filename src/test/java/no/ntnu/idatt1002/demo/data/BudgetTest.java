package no.ntnu.idatt1002.demo.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetTest {

  @Test
  void add() {
    Budget budget = new Budget();
    budget.add(ExpenseType.TYPE6,123);
    assertEquals(123,budget.getAmount(ExpenseType.TYPE6));
  }
}