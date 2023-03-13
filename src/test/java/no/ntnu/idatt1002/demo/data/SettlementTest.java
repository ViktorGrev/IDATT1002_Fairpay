package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.data.user.Member;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class SettlementTest {

  @Test
  void addExpenseTest() {
    Member member1 = new Member(123123, new Date(12));
    Member member2 = new Member(1231234, new Date(11));
    ArrayList<Member> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    BigDecimal bd = new BigDecimal(250);
    Expense expense = new Expense(ExpenseType.TYPE3,"expense1",bd);
    Settlement s = new Settlement("Settlement1",123123, members);
    s.addExpense(expense);
    assertTrue(s.getExpenses().contains(expense));
  }

  @Test
  void removeExpenseTest() {
    Member member1 = new Member(123123, new Date(12));
    Member member2 = new Member(1231234, new Date(11));
    ArrayList<Member> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    BigDecimal bd = new BigDecimal(250);
    Expense expense = new Expense(ExpenseType.TYPE3,"expense1",bd);
    Expense expense2 = new Expense(ExpenseType.TYPE2,"expense2",bd);
    Settlement s = new Settlement("Settlement1",123123, members);
    s.addExpense(expense);
    assertTrue(s.getExpenses().contains(expense));
    assertFalse(s.removeExpense(expense2));
    s.removeExpense(expense);
    assertTrue(s.getExpenses().isEmpty());
  }

  @Test
  void getSettlementsTest() {
    Member member1 = new Member(123123, new Date(12));
    Member member2 = new Member(1231234, new Date(11));
    ArrayList<Member> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    BigDecimal bd = new BigDecimal(250);
    Expense expense = new Expense(ExpenseType.TYPE3,"expense1",bd);
    Expense expense2 = new Expense(ExpenseType.TYPE2,"expense2",bd);
    Settlement s = new Settlement("Settlement1",123123, members);
    s.addExpense(expense);
    s.addExpense(expense2);
    assertEquals(2, s.getExpenses().size());
  }

  @Test
  void getSettlementNameTest() {
    Member member1 = new Member(123123, new Date(12));
    Member member2 = new Member(1231234, new Date(11));
    ArrayList<Member> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    Settlement s = new Settlement("Settlement1",123123, members);
    assertEquals("Settlement1",s.getSettlementName());
  }

  @Test
  void setSettlementNameTest() {
    Member member1 = new Member(123123, new Date(12));
    Member member2 = new Member(1231234, new Date(11));
    ArrayList<Member> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    Settlement s = new Settlement("Settlement1",123123, members);
    s.setSettlementName("Settlement2");
    assertEquals("Settlement2",s.getSettlementName());
  }

  @Test
  void getSettlementIdTest() {
    Member member1 = new Member(123123, new Date(12));
    Member member2 = new Member(1231234, new Date(11));
    ArrayList<Member> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    Settlement s = new Settlement("Settlement1",123123, members);
    assertEquals(123123,s.getSettlementId());
  }

  @Test
  void getMembersTest() {
    Member member1 = new Member(123123, new Date(12));
    Member member2 = new Member(1231234, new Date(11));
    ArrayList<Member> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    Settlement s = new Settlement("Settlement1",123123, members);
    assertEquals(2, s.getMembers().size());
  }

  @Test
  void getExpensesTest() {
    Member member1 = new Member(123123, new Date(12));
    Member member2 = new Member(1231234, new Date(11));
    ArrayList<Member> members = new ArrayList<>();
    members.add(member1);
    members.add(member2);
    Settlement s = new Settlement("Settlement1",123123, members);
    BigDecimal bd = new BigDecimal(250);
    Expense e = new Expense(ExpenseType.TYPE6, bd);
    Expense e2 = new Expense(ExpenseType.TYPE6, bd);
    s.addExpense(e);
    s.addExpense(e2);
    assertEquals(2, s.getExpenses().size());
  }
}