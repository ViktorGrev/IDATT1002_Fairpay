package no.ntnu.idatt1002.demo.data;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

  @Test
  void getTypeTest() {
    BigDecimal bd = new BigDecimal(123);
    Expense e = new Expense(ExpenseType.TYPE2, bd);
    assertEquals(ExpenseType.TYPE2, e.getType());
  }

  @Test
  void getNameTest() {
    BigDecimal bd = new BigDecimal(123);
    Expense e = new Expense(ExpenseType.TYPE2,"Expense 1", bd);
    assertEquals("Expense 1", e.getName());
  }

  @Test
  void hasNameTest() {
    BigDecimal bd = new BigDecimal(123);
    Expense e = new Expense(ExpenseType.TYPE2, bd);
    Expense e2 = new Expense(ExpenseType.TYPE2,"Expense 2", bd);
    assertFalse(e.hasName());
    assertTrue(e2.hasName());
  }

  @Test
  void getAmountTest() {
    BigDecimal bd = new BigDecimal(123);
    Expense e = new Expense(ExpenseType.TYPE2, bd);
    assertEquals(BigDecimal.valueOf(123), e.getAmount());
  }

  @Test
  void getDateTest() {
    BigDecimal bd = new BigDecimal(123);
    Expense e = new Expense(ExpenseType.TYPE2, bd);
    Date date = new Date();
    assertEquals(date, e.getDate());
  }
}