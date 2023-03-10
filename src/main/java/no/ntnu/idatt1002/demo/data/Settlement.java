package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.data.person.Member;

import java.util.ArrayList;

public class Settlement {
  private final ArrayList<Expense> expenses;
  private final ArrayList<Member> members;
  private String settlementName;
  private final long settlementId;

  public Settlement(String settlementName, long settlementId, ArrayList<Member> members) {
    this.settlementName = settlementName;
    this.settlementId = settlementId;
    this.members = members;
    expenses = new ArrayList<>();
  }

  public void addExpense(Expense expense) {
    expenses.add(expense);
  }

  public boolean removeExpense(Expense expense) {
    if(expenses.contains(expense)) {
      return expenses.remove(expense);
    } else {
      return false;
    }
  }

  public ArrayList<Expense> getExpenses() {
    return expenses;
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

  public ArrayList<Member> getMembers() {
    return members;
  }
}
