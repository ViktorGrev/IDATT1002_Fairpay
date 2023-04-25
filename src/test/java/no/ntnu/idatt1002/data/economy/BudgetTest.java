package no.ntnu.idatt1002.data.economy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Budget Test")
class BudgetTest {

  @Test
  @DisplayName("Add amount")
  void addTest() {
    ExpenseType type = ExpenseType.INTERNET;
    BigDecimal amount = BigDecimal.valueOf(123);
    Budget budget = new Budget();
    budget.add(type, amount);
    assertEquals(amount, budget.getAmount(type));
  }

  @Test
  @DisplayName("Add invalid amount")
  void addInvalidTest() {
    Budget budget = new Budget();
    assertThrows(IllegalArgumentException.class, () -> budget.add(ExpenseType.INTERNET, null));
    assertThrows(IllegalArgumentException.class, () -> budget.add(null, BigDecimal.valueOf(123)));
  }

  @Test
  @DisplayName("Get total")
  void getTotalTest() {
    ExpenseType type = ExpenseType.INTERNET;
    BigDecimal amount = BigDecimal.valueOf(123);
    Budget budget = new Budget();
    budget.add(type, amount);
    assertEquals(amount, budget.getTotal());
  }
}