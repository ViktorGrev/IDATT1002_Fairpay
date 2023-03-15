package no.ntnu.idatt1002.data;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

  @Test
  void addMemberTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    User newMember2 = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    Group group = new Group(123131,"NewGroup");
    group.addMember(newMember);
    assertFalse(group.addMember(newMember2));
    assertFalse(group.getGroup().isEmpty());
  }

  @Test
  void removeMemberTest() {
    User newMember = new User(123123, "newMember", "password", Date.from(Instant.now()), 12312312);
    Group group = new Group(123131,"NewGroup");
    group.addMember(newMember);
    assertFalse(group.getGroup().isEmpty());
    group.removeMember(newMember);
    assertTrue(group.getGroup().isEmpty());
  }

  @Test
  void getGroupIdTest() {
    Group group = new Group(123131,"NewGroup");
    assertEquals(123131, group.getGroupId());
  }

  @Test
  void getGroupNameTest() {
    Group group = new Group(123131,"NewGroup");
    assertEquals("NewGroup", group.getGroupName());
  }

  @Test
  void setGroupNameTest() {
    Group group = new Group(123131,"NewGroup");
    group.setGroupName("NewGroup2");
    assertEquals("NewGroup2", group.getGroupName());
  }
}