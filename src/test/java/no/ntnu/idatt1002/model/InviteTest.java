package no.ntnu.idatt1002.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Invite test")
class InviteTest {

  private final Date date = new Date();
  private final Invite invite = new Invite(1, 2, 3, date);

  @Test
  @DisplayName("Get group ID")
  void getGroup() {
    assertEquals(1, invite.getGroupId());
  }

  @Test
  @DisplayName("Get sender ID")
  void getSender() {
    assertEquals(2, invite.getSenderId());
  }

  @Test
  @DisplayName("Get target ID")
  void getTarget() {
    assertEquals(3, invite.getTargetId());
  }

  @Test
  @DisplayName("Get date")
  void getDate() {
    assertEquals(date, invite.getDate());
  }

  @Test
  @DisplayName("Equals")
  void equalsTest() {
    Invite invite1 = new Invite(1, 2, 3, date);
    assertEquals(invite, invite1);
  }

  @Test
  @DisplayName("Hashcode")
  void hashcodeTest() {
    Invite invite1 = new Invite(1, 2, 3, date);
    assertEquals(invite.hashCode(), invite1.hashCode());
  }
}