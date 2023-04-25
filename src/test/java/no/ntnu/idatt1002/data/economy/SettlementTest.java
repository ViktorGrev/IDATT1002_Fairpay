package no.ntnu.idatt1002.data.economy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Settlement test")
class SettlementTest {

  private final Expense expense = new Expense(1, 1, new Date(), ExpenseType.CAR,
          "Name", BigDecimal.valueOf(123), new Date(), 1);
  private final Settlement settlement = new Settlement(1, 1, "Settlement1", new Date(), false);

  @Test
  @DisplayName("Invalid expense")
  void invalidExpenseTest() {
    assertThrows(IllegalArgumentException.class, () -> new Expense(-1, 1, new Date(),
            ExpenseType.INTERNET, "Name", BigDecimal.ONE, new Date(), 1));
  }

  @Test
  @DisplayName("Add expense")
  void addExpenseTest() {
    settlement.addExpense(expense.getId());
    assertTrue(settlement.getExpenses().contains(expense.getId()));
  }

  @Test
  @DisplayName("Remove expense")
  void removeExpenseTest() {
    settlement.addExpense(expense.getId());
    settlement.removeExpense(expense.getId());
    assertTrue(settlement.getExpenses().isEmpty());
  }

  @Test
  @DisplayName("Get expenses")
  void getExpensesTest() {
    settlement.addExpense(expense.getId());
    assertEquals(1, settlement.getExpenses().size());
  }

  @Test
  @DisplayName("Get name")
  void getSettlementNameTest() {
    assertEquals("Settlement1", settlement.getName());
  }

  @Test
  @DisplayName("Set name")
  void setSettlementNameTest() {
    settlement.setName("Settlement2");
    assertEquals("Settlement2", settlement.getName());
  }

  @Test
  @DisplayName("Get ID")
  void getSettlementIdTest() {
    assertEquals(1, settlement.getId());
  }

  @Test
  @DisplayName("Get members")
  void getMembersTest() {
    settlement.addMember(1);
    settlement.addMember(2);
    assertEquals(2, settlement.getMembers().size());
  }
}