package no.ntnu.idatt1002.data;

import java.util.*;

/**
 * This class represents a group of users. Each group has a unique
 * ID, but not necessarily a unique name.
 */
public final class Group {

  public static long CURRENT = -1; // The current group ID

  private final long groupId; // The ID of the group
  private String name; // The name of the group
  private final List<User> members = new ArrayList<>(); // A list with the users within this group
  private final List<Long> expenses = new ArrayList<>();
  private final List<Long> income = new ArrayList<>();
  private final Map<Long, List<Long>> paidExpenses = new HashMap<>();
  private final Map<Long, List<Long>> receivedIncome = new HashMap<>();

  /**
   Constructs a new group with the specified group ID and group name.
   * @param   groupId the ID of the group
   * @param   name the name of the group
   */
  public Group(long groupId, String name) {
    this.groupId = groupId;
    this.name = name;
  }

  /**
   * Returns the ID of the group.
   * @return  the ID of the group
   */
  public long getId() {
    return groupId;
  }

  /**
   * Sets the name of the group.
   * @param   name the new name of the group
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the group.
   * @return  the name of the group
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a member to the group.
   * @param   user the user to add
   * @return  true if the member was successfully added, otherwise false
   */
  public boolean addMember(User user) {
    if(isMember(user.getId())) return false;
    members.add(user);
    return true;
  }

  /**
   * Removes a member from the group.
   * @param   user the user to remove
   * @return  true if the member was successfully removed, otherwise false
   */
  public boolean removeMember(User user) {
      return members.removeIf(member -> member.getId() == user.getId());
  }

  /**
   * Returns true if a member of this group has the specified ID.
   * @param   userId the ID of the user
   * @return  true if a member of this group has the specified ID
   */
  public boolean isMember(long userId) {
    return members.stream().anyMatch(user -> user.getId() == userId);
  }

  /**
   * Returns an unmodifiable list of the members of this group.
   * @return  an unmodifiable list of the members of this group
   */
  public List<User> getMembers() {
    return Collections.unmodifiableList(members);
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
   * Gets the list of expenses in the group.
   *
   * @return the list of expenses in the group
   */
  public List<Long> getExpenses() {
    return Collections.unmodifiableList(expenses);
  }

  /**
   * Adds an income to the list of incomes.
   * @param incomeId the income ID to be added
   * @return true if the income was added successfully, false if it was already in the list
   */
  public boolean addIncome(long incomeId) {
    return income.add(incomeId);
  }

  /**
   * Gets the list of income in the group.
   * @return the list of income in the group
   */
  public List<Long> getIncome() {
    return Collections.unmodifiableList(income);
  }

  public void addReceivedIncome(long incomeId, long userId) {
    if(!receivedIncome.containsKey(incomeId))
      receivedIncome.put(incomeId, new ArrayList<>());
    receivedIncome.get(incomeId).add(userId);
  }

  public void removeReceivedIncome(long incomeId, long userId) {
    if(!receivedIncome.containsKey(incomeId)) return;
    receivedIncome.get(incomeId).remove(userId);
  }

  public boolean isIncomeReceived(long incomeId, long userId) {
    return receivedIncome.getOrDefault(incomeId, new ArrayList<>()).contains(userId);
  }

  public Map<Long, List<Long>> getReceivedIncome() {
    return receivedIncome;
  }

  public void addPaidExpense(long expenseId, long userId) {
    if(!paidExpenses.containsKey(expenseId))
      paidExpenses.put(expenseId, new ArrayList<>());
    paidExpenses.get(expenseId).add(userId);
  }

  public void removePaidExpense(long expenseId, long userId) {
    if(!paidExpenses.containsKey(expenseId)) return;
    paidExpenses.get(expenseId).remove(userId);
  }

  public boolean isPaid(long expenseId, long userId) {
    return paidExpenses.getOrDefault(expenseId, new ArrayList<>()).contains(userId);
  }

  public Map<Long, List<Long>> getPaidExpenses() {
    return paidExpenses;
  }

  /**
   * Set the current group.
   * @param   group the group
   */
  public static void setCurrent(long groupId) {
    CURRENT = groupId;
  }
}