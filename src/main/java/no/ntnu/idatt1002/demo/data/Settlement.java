package no.ntnu.idatt1002.demo.data;

import java.util.ArrayList;

public class Settlement {
  private final ArrayList<Expense> settlements;
  private String settlementName;
  private final long settlementId;

  public Settlement(String settlementName, long settlementId) {
    this.settlementName = settlementName;
    this.settlementId = settlementId;
    settlements = new ArrayList<>();
  }

  public void addExpense(Expense expense) {
    settlements.add(expense);
  }

  public boolean removeExpense(Expense expense) {
    if(settlements.contains(expense)) {
      return settlements.remove(expense);
    } else {
      return false;
    }
  }

  public ArrayList<Expense> getSettlement() {
    return settlements;
  }

  public String getSettlementName() {
    return settlementName;
  }

  public void setSettlementName(String settlementName) {
    this.settlementName = settlementName;
  }

  public long getSettlementId() {
    return settlementId;
  }
}
