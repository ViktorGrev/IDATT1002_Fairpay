package no.ntnu.idatt1002.model;

import no.ntnu.idatt1002.model.economy.Expense;
import no.ntnu.idatt1002.model.economy.ExpenseType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Group test")
class GroupTest {

  private final Group group = new Group(1,"Group");

  @Test
  @DisplayName("Add member")
  void addMemberTest() {
    User member = new User(1, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    group.addMember(member);
    assertFalse(group.addMember(member));
    assertFalse(group.getMembers().isEmpty());
  }

  @Test
  @DisplayName("Remove member")
  void removeMemberTest() {
    User newMember = new User(2, "newMember", "password", Date.from(Instant.now()), Date.from(Instant.now()), 12312312);
    group.addMember(newMember);
    assertFalse(group.getMembers().isEmpty());
    group.removeMember(newMember);
    assertTrue(group.getMembers().isEmpty());
  }

  @Test
  @DisplayName("Get ID")
  void getGroupIdTest() {
    assertEquals(1, group.getId());
  }

  @Test
  @DisplayName("Get name")
  void getGroupNameTest() {
    assertEquals("Group", group.getName());
  }

  @Test
  @DisplayName("Set name")
  void setGroupNameTest() {
    group.setName("New Group");
    assertEquals("New Group", group.getName());
  }

  @Test
  @DisplayName("Equals")
  void equalsTest() {
    Group group1 = new Group(3, "Group1");
    Group group2 = new Group(3, "Group1");
    assertEquals(group1, group2);
  }

  @Test
  @DisplayName("Hashcode")
  void hashcodeTest() {
    Group group1 = new Group(3, "Group1");
    Group group2 = new Group(3, "Group1");
    assertEquals(group1.hashCode(), group2.hashCode());
  }
}