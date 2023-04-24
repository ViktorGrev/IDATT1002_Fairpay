package no.ntnu.idatt1002.data.economy;

import no.ntnu.idatt1002.data.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SettlementTest {

  private final Expense expense = new Expense(1, 1, addDate, ExpenseType.CAR,
          "Name", BigDecimal.valueOf(123), new Date(), 1);

  @Test
  void addExpenseTest() {
    Settlement s = new Settlement(1, 1, "Settlement1", new Date(), false, false);
    s.addExpense(expense.getId());
    assertTrue(s.getExpenses().contains(expense.getId()));
  }

  @Test
  void removeExpenseTest() {
    Settlement s = new Settlement(123123, 1, "Settlement1", new Date(), false, false);
    s.addExpense(expense.getId());
    s.removeExpense(expense.getId());
    assertTrue(s.getExpenses().isEmpty());
  }

  @Test
  void getExpensesTest() {
    Settlement s = new Settlement(123123, 1, "Settlement1", new Date(), false, false);
    s.addExpense(expense.getId());
    assertEquals(1, s.getExpenses().size());
  }

  @Test
  void getSettlementNameTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement(123123, 1, "Settlement1", new Date(), false, false);
    assertEquals("Settlement1",s.getName());
  }

  @Test
  void setSettlementNameTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement(123123, 1, "Settlement1", new Date(), false, false);
    s.setName("Settlement2");
    assertEquals("Settlement2",s.getName());
  }

  @Test
  void getSettlementIdTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement(123123, 1, "Settlement1", new Date(), false, false);
    assertEquals(123123,s.getId());
  }

  @Test
  void getMembersTest() {
    Settlement s = new Settlement(123123, 1, "Settlement1", new Date(), false, false);
    s.addMember(1);
    s.addMember(2);
    assertEquals(2, s.getMembers().size());
  }
}