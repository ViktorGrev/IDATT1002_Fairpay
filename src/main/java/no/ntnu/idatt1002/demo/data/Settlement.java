package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.data.user.Member;

import java.util.ArrayList;

public class Settlement {
  private final ArrayList<Expense> expenses;
  private final ArrayList<Member> members;
  private String settlementName;
  private final long settlementId;

  public Settlement(String settlementName, long settlementId, ArrayList<Member> members) {
    if(settlementName.isBlank()){
      throw new IllegalArgumentException("The settlement name cannot be blank");
    } if (settlementId > 0){
      throw new IllegalArgumentException("The settlementID cannot be below zero");
    } if (members.isEmpty()){
      throw new IllegalArgumentException("No members are added to the settlement");
    }

    this.settlementName = settlementName;
    this.settlementId = settlementId;
    this.members = members;
    expenses = new ArrayList<>();
  }

  public boolean addExpense(Expense expense) {
    if(expenses.contains(expense)) {
      return false;
    } else {
      return expenses.add(expense);
    }
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
    if(!settlementName.isBlank()) {
      this.settlementName = settlementName;
    } else {
      throw new IllegalArgumentException("The settlement name cannot be blank!");
    }
  }

  public long getSettlementId() {
    return settlementId;
  }

  public ArrayList<Member> getMembers() {
    return members;
  }
}
