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
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    BigDecimal bd = new BigDecimal(250);
    long personId = 123123;
    Expense expense = new Expense(ExpenseType.TYPE3,bd, personId);
    Settlement s = new Settlement("Settlement1",123123, members);
    s.addExpense(expense);
    assertTrue(s.getExpenses().contains(expense));
  }

  @Test
  void removeExpenseTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    BigDecimal bd = new BigDecimal(250);
    long personId = 123123;
    Expense expense = new Expense(ExpenseType.TYPE3,bd, personId);
    Expense expense2 = new Expense(ExpenseType.TYPE2,bd, personId);
    Settlement s = new Settlement("Settlement1",123123, members);
    s.addExpense(expense);
    assertTrue(s.getExpenses().contains(expense));
    assertFalse(s.removeExpense(expense2));
    s.removeExpense(expense);
    assertTrue(s.getExpenses().isEmpty());
  }

  @Test
  void getSettlementsTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    BigDecimal bd = new BigDecimal(250);
    long personId = 123123;
    Expense expense = new Expense(ExpenseType.TYPE3,bd, personId);
    Expense expense2 = new Expense(ExpenseType.TYPE2,bd, personId);
    Settlement s = new Settlement("Settlement1",123123, members);
    s.addExpense(expense);
    s.addExpense(expense2);
    assertEquals(2, s.getExpenses().size());
  }

  @Test
  void getSettlementNameTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement("Settlement1",123123, members);
    assertEquals("Settlement1",s.getSettlementName());
  }

  @Test
  void setSettlementNameTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement("Settlement1",123123, members);
    s.setSettlementName("Settlement2");
    assertEquals("Settlement2",s.getSettlementName());
  }

  @Test
  void getSettlementIdTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement("Settlement1",123123, members);
    assertEquals(123123,s.getSettlementId());
  }

  @Test
  void getMembersTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    User newMember2 = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement("Settlement1",123123, members);
    assertEquals(2, s.getMembers().size());
  }

  @Test
  void getExpensesTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    User newMember2 = new User(1231233, "newMember", "password", Date.from(Instant.now()), 12312312);
    ArrayList<User> members = new ArrayList<>();
    members.add(newMember);
    members.add(newMember2);
    Settlement s = new Settlement("Settlement1",123123, members);
    BigDecimal bd = new BigDecimal(250);
    long personId = 123123;
    Expense e = new Expense(ExpenseType.TYPE6, bd, personId);
    Expense e2 = new Expense(ExpenseType.TYPE6, bd, personId);
    s.addExpense(e);
    s.addExpense(e2);
    assertEquals(2, s.getExpenses().size());
  }
}