package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.GroupDAO;
import no.ntnu.idatt1002.dao.UserDAO;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GroupDAO")
public class SqlGroupDAOTest {

  private static UserDAO userDAO;
  private static GroupDAO groupDAO;

  @BeforeAll
  static void prepare() throws IOException {
    if(!FairPay.test) {
      FairPay.test = true;
      Files.delete(Path.of("test.db"));
    }
    userDAO = Database.getDAO(UserDAO.class);
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
    Group group = groupDAO.create("New group");
    groupDAO.addMember(group.getId(), groupUser.getId());
    Group group1 = groupDAO.find(group.getId());
    assertTrue(group1.isMember(groupUser.getId()));
  }

  @Test
  @DisplayName("Remove member")
  void removeMember() {
    User groupUser = userDAO.create("GroupUser2", "password", 12345678);
    Group group = groupDAO.create("New group");
    groupDAO.addMember(group.getId(), groupUser.getId());
    Group group1 = groupDAO.find(group.getId());
    assertTrue(group1.isMember(groupUser.getId()));
    groupDAO.removeMember(group.getId(), groupUser.getId());
    Group group2 = groupDAO.find(group.getId());
    assertFalse(group2.isMember(groupUser.getId()));
  }
}
