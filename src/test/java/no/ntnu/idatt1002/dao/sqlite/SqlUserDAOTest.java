package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.UserDAO;
import no.ntnu.idatt1002.dao.exception.AuthException;
import no.ntnu.idatt1002.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserDAO")
public class SqlUserDAOTest {

  private static UserDAO userDAO;

  @BeforeAll
  static void prepare() throws IOException {
    if(!FairPay.test) {
      FairPay.test = true;
      Files.deleteIfExists(Path.of("test.db"));
    }
    userDAO = Database.getDAO(UserDAO.class);
  }

  @Test
  @DisplayName("Create user")
  void createUser() {
    User user = userDAO.create("Bob", "password", 12345678);
    assertNotNull(user);
    assertEquals("Bob", user.getUsername());
    assertEquals(12345678, user.getPhoneNumber());
  }

  @Test
  @DisplayName("Find user")
  void findUser() {
    User user = userDAO.create("FindBob", "password", 12345678);
    assertNotNull(userDAO.find(user.getId()));
  }

  @Test
  @DisplayName("Find users")
  void findUsers() {
    User user1 = userDAO.create("FindBob1", "password", 12345678);
    User user2 = userDAO.create("FindBob2", "password", 12345678);
    assertEquals(2, userDAO.find(Arrays.asList(user1.getId(), user2.getId())).size());
  }

  @Test
  @DisplayName("Authentication")
  void authenticate() {
    userDAO.create("AuthUser", "password", 12345678);
    assertDoesNotThrow(() -> userDAO.authenticate("AuthUser", "password"));
    assertThrows(AuthException.class, () -> userDAO.authenticate("AuthUser", "wrongPassword"));
  }

  @Test
  @DisplayName("Update username")
  void setUsername() {
    User user = userDAO.create("NameTest", "password", 12345678);
    userDAO.setName(user.getId(), "Alice");
    assertNotNull(userDAO.find("Alice"));
  }

  @Test
  @DisplayName("Update number")
  void setPhoneNumber() {
    User user = userDAO.create("NumberTest", "password", 12345678);
    userDAO.setPhoneNumber(user.getId(), 87654321);
    User user1 = userDAO.find("NumberTest");
    assertEquals(87654321, user1.getPhoneNumber());
  }
}
