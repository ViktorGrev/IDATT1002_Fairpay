package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.IncomeDAO;
import no.ntnu.idatt1002.data.economy.Income;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("IncomeDAO")
public class SqlIncomeDAOTest {

  private static IncomeDAO incomeDAO;

  @BeforeAll
  static void prepare() throws IOException {
    if(!FairPay.test) {
      FairPay.test = true;
      Files.deleteIfExists(Path.of("test.db"));
    }
    incomeDAO = Database.getDAO(IncomeDAO.class);
  }

  @Test
  @DisplayName("Create income")
  void createIncome() {
    Date date = new Date();
    Income income = incomeDAO.create(1, "Test", BigDecimal.ONE, date, 1);
    assertNotNull(income);
    assertEquals(1, income.getUserId());
    assertEquals("Test", income.getName());
    assertEquals(BigDecimal.ONE, income.getAmount());
    assertEquals(date, income.getDate());
    assertEquals(1, income.getShares());
  }

  @Test
  @DisplayName("Delete income")
  void deleteIncome() {
    Income income = incomeDAO.create(1, "Test", BigDecimal.ONE, new Date(), 1);
    incomeDAO.delete(income.getId());
    assertEquals(0, incomeDAO.find(List.of(income.getId())).size());
  }
}
