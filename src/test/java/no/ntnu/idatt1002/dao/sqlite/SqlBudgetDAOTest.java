package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.dao.BudgetDAO;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.data.economy.Budget;
import no.ntnu.idatt1002.data.economy.ExpenseType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("BudgetDAO")
public class SqlBudgetDAOTest {

  private static BudgetDAO budgetDAO;

  @BeforeAll
  static void prepare() throws IOException {
    if(!FairPay.test) {
      FairPay.test = true;
      Files.delete(Path.of("test.db"));
    }
    budgetDAO = Database.getDAO(BudgetDAO.class);
  }

  @Test
  @DisplayName("Add type")
  void addType() {
    budgetDAO.addType(1, ExpenseType.INTERNET, BigDecimal.TEN);
    Budget budget = budgetDAO.find(1L);
    assertEquals(BigDecimal.TEN, budget.getAmount(ExpenseType.INTERNET));
  }

  @Test
  @DisplayName("Get total")
  void getTotal() {
    budgetDAO.addType(2, ExpenseType.INTERNET, BigDecimal.TEN);
    Budget budget = budgetDAO.find(2L);
    assertEquals(BigDecimal.TEN, budget.getTotal());
  }
}
