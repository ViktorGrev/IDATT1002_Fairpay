package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.data.user.Member;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

  @Test
  void addMemberTest() {
    Member newMember = new Member(123123,new Date(12-11-1212));
    Group group = new Group(123131,"NewGroup");
    group.addMember(newMember);
    assertFalse(group.getGroup().isEmpty());
  }

  @Test
  void removeMemberTest() {
    Member newMember = new Member(123123,new Date(12-11-1212));
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