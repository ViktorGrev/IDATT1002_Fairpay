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
}
