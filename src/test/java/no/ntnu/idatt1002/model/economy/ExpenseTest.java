package no.ntnu.idatt1002.model.economy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Expense test")
class ExpenseTest {

  private final long time = System.currentTimeMillis();
  private final Expense expense = new Expense(1, 1, new Date(), ExpenseType.CAR,
          "Name", BigDecimal.valueOf(123), new Date(time), 1);

  @Test
  @DisplayName("New expense")
  void newExpenseTest() {
    assertThrows(IllegalArgumentException.class, () -> new Expense(1, 1, null, ExpenseType.CAR,
            "Name", BigDecimal.valueOf(123), new Date(time), 1));
  }

  @Test
  @DisplayName("Get type")
  void getTypeTest() {
    assertEquals(ExpenseType.CAR, expense.getType());
  }

  @Test
  @DisplayName("Get name")
  void getNameTest() {
    assertEquals("Name", expense.getName());
  }

  @Test
  @DisplayName("Has name")
  void hasNameTest() {
    assertTrue(expense.hasName());
  }

  @Test
  @DisplayName("Has no name")
  void hasNoNameTest() {
    Expense expense = new Expense(1, 1, new Date(), ExpenseType.CAR, null, BigDecimal.valueOf(1), new Date(), 1);
    assertFalse(expense.hasName());
  }

  @Test
  @DisplayName("Get amount")
  void getAmountTest() {
    assertEquals(BigDecimal.valueOf(123), expense.getAmount());
  }

  @Test
  @DisplayName("Get date")
  void getDateTest() {
    Date date = new Date(time);
    assertEquals(date, expense.getDate());
  }

  @Test
  @DisplayName("Expense type")
  void expenseType() {
    ExpenseType type = ExpenseType.CAR;
    assertEquals(type, ExpenseType.fromName(type.getName()));
    assertEquals(type, ExpenseType.fromNumber(type.getNumber()));
  }

  @Test
  @DisplayName("Expense type name")
  void expenseTypeName() {
    assertEquals("Car", ExpenseType.CAR.getName());
  }

  @Test
  @DisplayName("Equals")
  void equalsTest() {
    Date date = new Date();
    Expense expense1 = new Expense(1, 1, date, ExpenseType.FOOD, null, BigDecimal.ONE, date, 1);
    Expense expense2 = new Expense(1, 1, date, ExpenseType.FOOD, null, BigDecimal.ONE, date, 1);
    assertEquals(expense1, expense2);
  }

  @Test
  @DisplayName("Hashcode")
  void hashcodeTest() {
    Date date = new Date();
    Expense expense1 = new Expense(1, 1, date, ExpenseType.FOOD, null, BigDecimal.ONE, date, 1);
    Expense expense2 = new Expense(1, 1, date, ExpenseType.FOOD, null, BigDecimal.ONE, date, 1);
    assertEquals(expense1.hashCode(), expense2.hashCode());
  }
}