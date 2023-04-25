package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.ExpenseDAO;
import no.ntnu.idatt1002.data.economy.Expense;
import no.ntnu.idatt1002.data.economy.ExpenseType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ExpenseDAO")
public class SqlExpenseDAOTest {

  private static ExpenseDAO expenseDAO;

  @BeforeAll
  static void prepare() throws IOException {
    if(!FairPay.test) {
      FairPay.test = true;
      Files.deleteIfExists(Path.of("test.db"));
    }
    expenseDAO = Database.getDAO(ExpenseDAO.class);
  }

  @Test
  @DisplayName("Create expense")
  void createExpense() {
    Date date = new Date();
    Expense expense = expenseDAO.create(1, ExpenseType.INTERNET,
            "Test", BigDecimal.ONE, date, 1);
    assertNotNull(expense);
    assertEquals(1, expense.getUserId());
    assertEquals(ExpenseType.INTERNET, expense.getType());
    assertEquals("Test", expense.getName());
    assertEquals(BigDecimal.ONE, expense.getAmount());
    assertEquals(date, expense.getDate());
    assertEquals(1, expense.getShares());
  }

  @Test
  @DisplayName("Delete expense")
  void deleteExpense() {
    Expense expense = expenseDAO.create(1, ExpenseType.INTERNET,
            "Test", BigDecimal.ONE, new Date(), 1);
    expenseDAO.delete(expense.getId());
    assertEquals(0, expenseDAO.find(List.of(expense.getId())).size());
  }
}
