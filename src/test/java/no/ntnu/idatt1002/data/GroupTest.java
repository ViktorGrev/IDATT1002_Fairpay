package no.ntnu.idatt1002.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}