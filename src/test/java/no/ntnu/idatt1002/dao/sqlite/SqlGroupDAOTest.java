package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.dao.*;
import no.ntnu.idatt1002.model.Group;
import no.ntnu.idatt1002.model.User;
import no.ntnu.idatt1002.model.economy.Expense;
import no.ntnu.idatt1002.model.economy.ExpenseType;
import no.ntnu.idatt1002.model.economy.Income;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GroupDAO")
public class SqlGroupDAOTest {

  private static UserDAO userDAO;
  private static ExpenseDAO expenseDAO;
  private static IncomeDAO incomeDAO;
  private static GroupDAO groupDAO;

  @BeforeAll
  static void prepare() throws IOException {
    if(!FairPay.test) {
      FairPay.test = true;
      Files.deleteIfExists(Path.of("test.db"));
    }
    userDAO = Database.getDAO(UserDAO.class);
    expenseDAO = Database.getDAO(ExpenseDAO.class);
    incomeDAO = Database.getDAO(IncomeDAO.class);
    groupDAO = Database.getDAO(GroupDAO.class);
  }

  @Test
  @DisplayName("Create group")
  void createGroup() {
    Group group = groupDAO.create("New group");
    assertNotNull(group);
    assertEquals("New group", group.getName());
  }

  @Test
  @DisplayName("Add member")
  void addMember() {
    User groupUser = userDAO.create("GroupUser1", "password", 12345678);
    Group group = groupDAO.create("New group 1");
    groupDAO.addMember(group.getId(), groupUser.getId());
    Group group1 = groupDAO.find(group.getId());
    assertTrue(group1.isMember(groupUser.getId()));
  }

  @Test
  @DisplayName("Remove member")
  void removeMember() {
    User groupUser = userDAO.create("GroupUser2", "password", 12345678);
    Group group = groupDAO.create("New group 2");
    groupDAO.addMember(group.getId(), groupUser.getId());
    Group group1 = groupDAO.find(group.getId());
    assertTrue(group1.isMember(groupUser.getId()));
    groupDAO.removeMember(group.getId(), groupUser.getId());
    Group group2 = groupDAO.find(group.getId());
    assertFalse(group2.isMember(groupUser.getId()));
  }

  @Test
  @DisplayName("Find member")
  void findMember() {
    Group group1 = groupDAO.create("Group1");
    Group group2 = groupDAO.create("Group2");
    List<Group> groups = groupDAO.find(Arrays.asList(group1.getId(), group2.getId()));
    assertEquals(2, groups.size());
  }

  @Test
  @DisplayName("Handle invite")
  void addInvite() {
    groupDAO.addInvite(1, 1, 1);
    assertTrue(groupDAO.getInvites(1).stream().anyMatch(i -> i.getTargetId() == 1));
    groupDAO.removeInvite(1, 1);
    assertFalse(groupDAO.getInvites(1).stream().anyMatch(i -> i.getTargetId() == 1));
  }

  @Test
  @DisplayName("Expense")
  void expenseTest() {
    Group group = groupDAO.create("ExpenseGroup");
    groupDAO.addExpense(group.getId(), 3);
    assertTrue(groupDAO.find(group.getId()).getExpenses().contains(3L));
    groupDAO.removeExpense(group.getId(), 3);
    assertFalse(groupDAO.find(group.getId()).getExpenses().contains(3L));
  }

  @Test
  @DisplayName("Income")
  void incomeTest() {
    Group group = groupDAO.create("IncomeGroup");
    groupDAO.addIncome(group.getId(), 3);
    assertTrue(groupDAO.find(group.getId()).getIncome().contains(3L));
    groupDAO.removeIncome(group.getId(), 3);
    assertFalse(groupDAO.find(group.getId()).getIncome().contains(3L));
  }

  @Test
  @DisplayName("Find by user")
  void findByUser() {
    Group group = groupDAO.create("UserGroup");
    groupDAO.addMember(group.getId(), 3);
    assertNotNull(groupDAO.findByUser(3));
  }

  @Test
  @DisplayName("Invites by user")
  void invitesByUser() {
    groupDAO.addInvite(6, 1, 7);
    assertTrue(groupDAO.getInvitesByUser(7).stream().anyMatch(i -> i.getGroupId() == 6));
  }

  @Test
  @DisplayName("Set name")
  void setName() {
    Group group = groupDAO.create("NameGroup");
    groupDAO.setName(group.getId(), "New Name");
    assertEquals("New Name", groupDAO.find(group.getId()).getName());
  }

  @Test
  @DisplayName("Set paid expense")
  void setPaid() {
    Group group = groupDAO.create("ExpenseGroup1");
    Expense expense = expenseDAO.create(1, ExpenseType.CAR, "Name", BigDecimal.ONE,
            new Date(), 1);
    groupDAO.addExpense(group.getId(), expense.getId());
    groupDAO.setPaidExpense(expense.getId(), 1);
    assertTrue(groupDAO.find(group.getId()).getPaidExpenses().containsKey(expense.getId()));
    groupDAO.unsetPaidExpense(expense.getId(), 1);
    assertFalse(groupDAO.find(group.getId()).getPaidExpenses().containsKey(expense.getId()));
  }

  @Test
  @DisplayName("Set received income")
  void setReceived() {
    Group group = groupDAO.create("IncomeGroup1");
    Income income = incomeDAO.create(1, "Income", BigDecimal.ONE, new Date(), 1);
    groupDAO.addIncome(group.getId(), income.getId());
    groupDAO.setReceivedIncome(income.getId(), 1);
    assertTrue(groupDAO.find(group.getId()).getReceivedIncome().containsKey(income.getId()));
    groupDAO.unsetReceivedIncome(income.getId(), 1);
    assertFalse(groupDAO.find(group.getId()).getReceivedIncome().containsKey(income.getId()));
  }
}
