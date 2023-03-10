package no.ntnu.idatt1002.demo.data;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SettlementTest {

  @Test
  void addExpenseTest() {
    BigDecimal bd = new BigDecimal(250);
    Expense expense = new Expense(ExpenseType.TYPE3,"expense1",bd);
    Settlement s = new Settlement("Settlement1",123123);
    s.addExpense(expense);
    assertTrue(s.getSettlement().contains(expense));
  }

  @Test
  void removeExpenseTest() {
    BigDecimal bd = new BigDecimal(250);
    Expense expense = new Expense(ExpenseType.TYPE3,"expense1",bd);
    Expense expense2 = new Expense(ExpenseType.TYPE2,"expense2",bd);
    Settlement s = new Settlement("Settlement1",123123);
    s.addExpense(expense);
    assertTrue(s.getSettlement().contains(expense));
    assertFalse(s.removeExpense(expense2));
    s.removeExpense(expense);
    assertTrue(s.getSettlement().isEmpty());
  }

  @Test
  void getSettlementsTest() {
    BigDecimal bd = new BigDecimal(250);
    Expense expense = new Expense(ExpenseType.TYPE3,"expense1",bd);
    Expense expense2 = new Expense(ExpenseType.TYPE2,"expense2",bd);
    Settlement s = new Settlement("Settlement1",123123);
    s.addExpense(expense);
    s.addExpense(expense2);
    assertEquals(2, s.getSettlement().size());
  }

  @Test
  void getSettlementNameTest() {
    Settlement s = new Settlement("Settlement1",123123);
    assertEquals("Settlement1",s.getSettlementName());
  }

  @Test
  void setSettlementNameTest() {
    Settlement s = new Settlement("Settlement1",123123);
    s.setSettlementName("Settlement2");
    assertEquals("Settlement2",s.getSettlementName());
  }

  @Test
  void getSettlementIdTest() {
    Settlement s = new Settlement("Settlement1",123123);
    assertEquals(123123,s.getSettlementId());
  }
}