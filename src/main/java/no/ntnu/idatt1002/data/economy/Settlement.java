package no.ntnu.idatt1002.data.economy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * The Settlement class represents a settlement with a name, a unique ID, and a list of members and expenses.
 */
public class Settlement {

  private final long id;
  private final long userId;
  private String settlementName;
  private final Date date;
  private final List<Long> expenses;
  private final List<Long> members;
  private boolean ended;
  private boolean deleted;

  /**
   * Constructs a new Settlement object with a given name, ID, and list of members.
   *
   * @param settlementId   the ID of the settlement
   * @param userId
   * @param settlementName the name of the settlement
   * @param date
   * @throws IllegalArgumentException if the settlement name is blank, the settlement ID is negative, or the list of members is empty
   */
  public Settlement(long settlementId, long userId, String settlementName, Date date, boolean ended, boolean deleted) {
    if(settlementName.isBlank()){
      throw new IllegalArgumentException("The settlement name cannot be blank");
    }
    if(settlementId < 0){
      throw new IllegalArgumentException("The settlement ID cannot be below zero");
    }

    this.settlementName = settlementName;
    this.id = settlementId;
    this.userId = userId;
    this.members = new ArrayList<>();
    this.expenses = new ArrayList<>();
    this.date = date;
    this.ended = ended;
    this.deleted = deleted;
  }

  /**
   * Gets the unique ID of the settlement.
   *
   * @return the unique ID of the settlement
   */
  public long getId() {
    return id;
  }

  /**
   * Returns the ID of the user who created the settlement.
   * @return  the ID of the user who created the settlement
   */
  public long getUserId() {
    return userId;
  }

  /**
   * Sets the name of the settlement.
   *
   * @param settlementName the new name of the settlement
   * @throws IllegalArgumentException if the new settlement name is blank
   */
  public void setName(String settlementName) {
    if(settlementName == null || settlementName.isBlank())
      throw new IllegalArgumentException("The settlement name cannot be null or blank!");
    this.settlementName = settlementName;
  }

  /**
   * Gets the name of the settlement.
   *
   * @return the name of the settlement
   */
  public String getName() {
    return settlementName;
  }

  /**
   * Adds an expense to the list of expenses.
   * @param expenseId the expense ID to be added
   * @return true if the expense was added successfully, false if it was already in the list
   */
  public boolean addExpense(long expenseId) {
    return expenses.add(expenseId);
  }

  /**
   * Removes an expense from the list of expenses.
   * @param expenseId the expense ID to be removed
   * @return true if the expense was removed successfully, false if it was not in the list
   */
  public boolean removeExpense(long expenseId) {
    return expenses.remove(expenseId);
  }

  /**
   * Gets the list of expenses in the settlement.
   *
   * @return the list of expenses in the settlement
   */
  public List<Long> getExpenses() {
    return Collections.unmodifiableList(expenses);
  }

  /**
   * Adds a member to this settlement.
   * @param   userId the ID of the user to add
   */
  public void addMember(long userId) {
    members.add(userId);
  }

  public void removeMember(long userId) {
    members.remove(userId);
  }

  /**
   * Gets the list of members in the settlement.
   *
   * @return the list of members in the settlement
   */
  public List<Long> getMembers() {
    return Collections.unmodifiableList(members);
  }

  public Date getDate() {
    return date;
  }

  public void setEnded(boolean ended) {
    this.ended = ended;
  }

  public boolean isEnded() {
    return ended;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean isDeleted() {
    return deleted;
  }
}
