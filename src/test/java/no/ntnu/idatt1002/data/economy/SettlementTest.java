package no.ntnu.idatt1002.data.economy;

import no.ntnu.idatt1002.data.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SettlementTest {

  @Test
  void addExpenseTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<Long> members = new ArrayList<>();
    members.add(newMember.getId());
    members.add(newMember2.getId());
    BigDecimal bd = new BigDecimal(250);
    long personId = 123123;
    Expense expense = new Expense(123, ExpenseType.TYPE3,bd, personId);
    Settlement s = new Settlement(123123, 1, "Settlement1", date);
    s.addExpense(expense.getExpenseId());
    assertTrue(s.getExpenses().contains(expense.getExpenseId()));
  }

  @Test
  void removeExpenseTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    BigDecimal bd = new BigDecimal(250);
    long personId = 123123;
    Expense expense = new Expense(1, ExpenseType.TYPE3,bd, personId);
    Expense expense2 = new Expense(2, ExpenseType.TYPE2,bd, personId);
    Settlement s = new Settlement(123123, 1, "Settlement1", date);
    s.addExpense(expense.getExpenseId());
    assertTrue(s.getExpenses().contains(expense.getExpenseId()));
    assertFalse(s.removeExpense(expense2.getExpenseId()));
    s.removeExpense(expense.getExpenseId());
    assertTrue(s.getExpenses().isEmpty());
  }

  @Test
  void getSettlementsTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    BigDecimal bd = new BigDecimal(250);
    long personId = 123123;
    Expense expense = new Expense(1, ExpenseType.TYPE3,bd, personId);
    Expense expense2 = new Expense(2, ExpenseType.TYPE2,bd, personId);
    Settlement s = new Settlement(123123, 1, "Settlement1", date);
    s.addExpense(expense.getExpenseId());
    s.addExpense(expense2.getExpenseId());
    assertEquals(2, s.getExpenses().size());
  }

  @Test
  void getSettlementNameTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement(123123, 1, "Settlement1", date);
    assertEquals("Settlement1",s.getName());
  }

  @Test
  void setSettlementNameTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement(123123, 1, "Settlement1", date);
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
    Settlement s = new Settlement(123123, 1, "Settlement1", date);
    assertEquals(123123,s.getId());
  }

  @Test
  void getMembersTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement(123123, 1, "Settlement1", date);
    assertEquals(2, s.getMembers().size());
  }

  @Test
  void getExpensesTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement(123123, 1, "Settlement1", date);
    BigDecimal bd = new BigDecimal(250);
    long personId = 123123;
    Expense e = new Expense(1, ExpenseType.TYPE6, bd, personId);
    Expense e2 = new Expense(2, ExpenseType.TYPE6, bd, personId);
    s.addExpense(e.getExpenseId());
    s.addExpense(e2.getExpenseId());
    assertEquals(2, s.getExpenses().size());
  }
}