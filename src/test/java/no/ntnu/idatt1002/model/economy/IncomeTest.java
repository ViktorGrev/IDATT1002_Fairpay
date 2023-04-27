package no.ntnu.idatt1002.model.economy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Income test")
class IncomeTest {

  private final long time = System.currentTimeMillis();
  private final Income income = new Income(1, 1, BigDecimal.valueOf(123),
          "Name", new Date(), new Date(time), 1);

  @Test
  @DisplayName("New income")
  void newIncomeTest() {
    assertThrows(IllegalArgumentException.class, () -> new Income(1, 1, null,
            "Name", null, null, 1));
  }

  @Test
  @DisplayName("Get name")
  void getNameTest() {
    assertEquals("Name", income.getName());
  }

  @Test
  @DisplayName("Get amount")
  void getAmountTest() {
    assertEquals(BigDecimal.valueOf(123), income.getAmount());
  }

  @Test
  @DisplayName("Get date")
  void getDateTest() {
    Date date = new Date(time);
    assertEquals(date, income.getDate());
  }

  @Test
  @DisplayName("Equals")
  void equalsTest() {
    Date date = new Date();
    Income income1 = new Income(1, 1, BigDecimal.ONE, "Name", date, date, 1);
    Income income2 = new Income(1, 1, BigDecimal.ONE, "Name", date, date, 1);
    assertEquals(income1, income2);
  }

  @Test
  @DisplayName("Hashcode")
  void hashcodeTest() {
    Date date = new Date();
    Income income1 = new Income(1, 1, BigDecimal.ONE, "Name", date, date, 1);
    Income income2 = new Income(1, 1, BigDecimal.ONE, "Name", date, date, 1);
    assertEquals(income1.hashCode(), income2.hashCode());
  }
}