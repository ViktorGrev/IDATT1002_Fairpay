package no.ntnu.idatt1002.data.economy;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

  private final long time = System.currentTimeMillis();
  private final Expense expense = new Expense(1, 1, addDate, ExpenseType.CAR,
          "Name", BigDecimal.valueOf(123), new Date(time), 1);

  @Test
  void getTypeTest() {
    assertEquals(ExpenseType.CAR, expense.getType());
  }

  @Test
  void getNameTest() {
    assertEquals("Name", expense.getName());
  }

  @Test
  void hasNameTest() {
    assertTrue(expense.hasName());
  }

  @Test
  void hasNoNameTest() {
    Expense expense = new Expense(1, 1, addDate, ExpenseType.CAR, null, BigDecimal.valueOf(1), new Date(), 1);
    assertFalse(expense.hasName());
  }

  @Test
  void getAmountTest() {
    assertEquals(BigDecimal.valueOf(123), expense.getAmount());
  }

  @Test
  void getDateTest() {
    Date date = new Date(time);
    assertEquals(date, expense.getDate());
  }
}