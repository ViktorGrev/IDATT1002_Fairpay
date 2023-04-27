package no.ntnu.idatt1002.dao.sqlite;

import no.ntnu.idatt1002.FairPay;
import no.ntnu.idatt1002.dao.Database;
import no.ntnu.idatt1002.dao.SettlementDAO;
import no.ntnu.idatt1002.model.economy.Settlement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SettlementDAO")
public class SqlSettlementDAOTest {

  private static SettlementDAO settlementDAO;

  @BeforeAll
  static void prepare() throws IOException {
    if(!FairPay.test) {
      FairPay.test = true;
      Files.deleteIfExists(Path.of("test.db"));
    }
    settlementDAO = Database.getDAO(SettlementDAO.class);
  }

  @Test
  @DisplayName("Create settlement")
  void createSettlement() {
    Settlement settlement = settlementDAO.create("Settlement 1", 1);
    assertNotNull(settlement);
    assertEquals("Settlement 1", settlement.getName());
    assertEquals(1, settlement.getUserId());
  }

  @Test
  @DisplayName("Delete settlement")
  void deleteSettlement() {
    Settlement settlement = settlementDAO.create("Settlement 1", 1);
    assertNotNull(settlement);
    settlementDAO.delete(settlement.getId());
    assertNull(settlementDAO.find(settlement.getId()));
  }

  @Test
  @DisplayName("Members")
  void memberTest() {
    Settlement settlement = settlementDAO.create("Settlement 1", 1);
    settlementDAO.addMember(settlement.getId(), 2);
    assertTrue(settlementDAO.find(settlement.getId()).getMembers().contains(2L));
    settlementDAO.removeMember(settlement.getId(), 2);
    assertFalse(settlementDAO.find(settlement.getId()).getMembers().contains(2L));
  }

  @Test
  @DisplayName("Expenses")
  void expensesTest() {
    Settlement settlement = settlementDAO.create("Settlement 1", 1);
    settlementDAO.addExpense(settlement.getId(), 2);
    assertTrue(settlementDAO.find(settlement.getId()).getExpenses().contains(2L));
  }

  @Test
  @DisplayName("Find by user")
  void findByUser() {
    Settlement settlement = settlementDAO.create("Settlement 1", 1);
    settlementDAO.addMember(settlement.getId(), 10);
    assertTrue(settlementDAO.findByUser(10).stream().anyMatch(s -> s.getId() == settlement.getId()));
  }

  @Test
  @DisplayName("Set ended")
  void end() {
    Settlement settlement = settlementDAO.create("Settlement 1", 1);
    settlementDAO.setEnded(settlement.getId(), true);
    assertTrue(settlementDAO.find(settlement.getId()).isEnded());
  }
}
