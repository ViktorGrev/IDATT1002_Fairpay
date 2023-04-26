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
}