package no.ntnu.idatt1002.data.economy;

import no.ntnu.idatt1002.data.User;

import java.util.ArrayList;

/**
 * The Settlement class represents a settlement with a name, a unique ID, and a list of members and expenses.
 */
public class Settlement {
  private final ArrayList<Expense> expenses;
  private final ArrayList<User> members;
  private String settlementName;
  private final long settlementId;

  /**
   * Constructs a new Settlement object with a given name, ID, and list of members.
   *
   * @param settlementName the name of the settlement
   * @param settlementId the ID of the settlement
   * @param members the list of members in the settlement
   * @throws IllegalArgumentException if the settlement name is blank, the settlement ID is negative, or the list of members is empty
   */
  public Settlement(String settlementName, long settlementId, ArrayList<User> members) {
    if(settlementName.isBlank()){
      throw new IllegalArgumentException("The settlement name cannot be blank");
    } if (settlementId < 0){
      throw new IllegalArgumentException("The settlementID cannot be below zero");
    } if (members.isEmpty()){
      throw new IllegalArgumentException("No members are added to the settlement");
    }

    this.settlementName = settlementName;
    this.settlementId = settlementId;
    this.members = members;
    expenses = new ArrayList<>();
  }

  /**
   * Adds an expense to the list of expenses.
   *
   * @param expense the expense to be added
   * @return true if the expense was added successfully, false if it was already in the list
   */
  public boolean addExpense(Expense expense) {
    if(expenses.contains(expense)) {
      return false;
    } else {
      return expenses.add(expense);
    }
  }

  /**
   * Removes an expense from the list of expenses.
   *
   * @param expense the expense to be removed
   * @return true if the expense was removed successfully, false if it was not in the list
   */
  public boolean removeExpense(Expense expense) {
    if(expenses.contains(expense)) {
      return expenses.remove(expense);
    } else {
      return false;
    }
  }

  /**
   * Gets the list of expenses in the settlement.
   *
   * @return the list of expenses in the settlement
   */
  public ArrayList<Expense> getExpenses() {
    return expenses;
  }

  /**
   * Gets the name of the settlement.
   *
   * @return the name of the settlement
   */
  public String getSettlementName() {
    return settlementName;
  }

  /**
   * Sets the name of the settlement.
   *
   * @param settlementName the new name of the settlement
   * @throws IllegalArgumentException if the new settlement name is blank
   */
  public void setSettlementName(String settlementName) {
    if(!settlementName.isBlank()) {
      this.settlementName = settlementName;
    } else {
      throw new IllegalArgumentException("The settlement name cannot be blank!");
    }
  }

  /**
   * Gets the unique ID of the settlement.
   *
   * @return the unique ID of the settlement
   */
  public long getSettlementId() {
    return settlementId;
  }

  /**
   * Gets the list of members in the settlement.
   *
   * @return the list of members in the settlement
   */
  public ArrayList<User> getMembers() {
    return members;
  }
}
