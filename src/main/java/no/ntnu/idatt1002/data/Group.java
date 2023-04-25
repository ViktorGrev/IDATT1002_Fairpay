package no.ntnu.idatt1002.data;

import java.util.*;

/**
 * This class represents a group of users. Each group has a unique
 * ID, but not necessarily a unique name.
 */
public final class Group {

  public static long CURRENT = -1; // The current group ID

  private final long id; // The ID of the group
  private String name; // The name of the group
  private final List<User> members = new ArrayList<>(); // A list of the users within this group
  private final List<Long> expenses = new ArrayList<>(); // A list of the expenses within this group
  private final List<Long> income = new ArrayList<>(); // A list of the income within this group
  // A map of the paid expenses within this group
  private final Map<Long, List<Long>> paidExpenses = new HashMap<>();
  // A map of the received income within this group
  private final Map<Long, List<Long>> receivedIncome = new HashMap<>();

  /**
   * Constructs a group with the specified values.
   *
   * @param   id the group ID
   * @param   name the group name
   */
  public Group(long id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Returns the group ID.
   *
   * @return  the group ID
   */
  public long getId() {
    return id;
  }

  /**
   * Sets the name of the group.
   *
   * @param   name the new name of the group
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the group.
   *
   * @return  the name of the group
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a member to the group.
   *
   * @param   user the user to add
   * @return  true if the member was successfully added, otherwise false
   */
  public boolean addMember(User user) {
    if (isMember(user.getId())) return false;
    members.add(user);
    return true;
  }

  /**
   * Removes a member from the group.
   *
   * @param   user the user to remove
   * @return  true if the member was successfully removed, otherwise false
   */
  public boolean removeMember(User user) {
    return members.removeIf(member -> member.getId() == user.getId());
  }

  /**
   * Returns true if a member of this group has the specified ID.
   *
   * @param   userId the ID of the user
   * @return  true if a member of this group has the specified ID
   */
  public boolean isMember(long userId) {
    return members.stream().anyMatch(user -> user.getId() == userId);
  }

  /**
   * Returns an unmodifiable list of the members of this group.
   *
   * @return  an unmodifiable list of the members of this group
   */
  public List<User> getMembers() {
    return Collections.unmodifiableList(members);
  }

  /**
   * Adds an expense to the list of expenses.
   *
   * @param   expenseId the expense ID to be added
   * @return  true if the expense was added successfully, false if it was already in the list
   */
  public boolean addExpense(long expenseId) {
    return expenses.add(expenseId);
  }

  /**
   * Removes an expense from the list of expenses.
   *
   * @param   expenseId the expense ID to be removed
   * @return  true if the expense was removed successfully, false if it was not in the list
   */
  public boolean removeExpense(long expenseId) {
    return expenses.remove(expenseId);
  }

  /**
   * Returns the list of expenses in the group.
   *
   * @return  the list of expenses in the group
   */
  public List<Long> getExpenses() {
    return Collections.unmodifiableList(expenses);
  }

  /**
   * Adds an income to the list of incomes.
   *
   * @param   incomeId the income ID to be added
   * @return  true if the income was added successfully, false if it was already in the list
   */
  public boolean addIncome(long incomeId) {
    return income.add(incomeId);
  }

  /**
   * Returns the list of income in the group.
   *
   * @return  the list of income in the group
   */
  public List<Long> getIncome() {
    return Collections.unmodifiableList(income);
  }

  /**
   * Adds a received income for a user.
   *
   * @param   incomeId the income ID
   * @param   userId the user ID
   */
  public void addReceivedIncome(long incomeId, long userId) {
    if (!receivedIncome.containsKey(incomeId))
      receivedIncome.put(incomeId, new ArrayList<>());
    receivedIncome.get(incomeId).add(userId);
  }

  /**
   * Remove a received income for a user.
   *
   * @param   incomeId the income ID
   * @param   userId the user ID
   */
  public void removeReceivedIncome(long incomeId, long userId) {
    if (!receivedIncome.containsKey(incomeId)) return;
    receivedIncome.get(incomeId).remove(userId);
  }

  /**
   * Returns true if the income is received by the user.
   *
   * @param   incomeId the expense ID
   * @param   userId the user iD
   * @return  true if the income is received by the user
   */
  public boolean isIncomeReceived(long incomeId, long userId) {
    return receivedIncome.getOrDefault(incomeId, new ArrayList<>()).contains(userId);
  }

  /**
   * Returns a map of the received income.
   *
   * @return  a map of the received income
   */
  public Map<Long, List<Long>> getReceivedIncome() {
    return receivedIncome;
  }

  /**
   * Adds a paid expense for a user.
   *
   * @param   expenseId the income ID
   * @param   userId the user ID
   */
  public void addPaidExpense(long expenseId, long userId) {
    if (!paidExpenses.containsKey(expenseId))
      paidExpenses.put(expenseId, new ArrayList<>());
    paidExpenses.get(expenseId).add(userId);
  }

  /**
   * Remove a paid expense for a user.
   *
   * @param   expenseId the expense ID
   * @param   userId the user ID
   */
  public void removePaidExpense(long expenseId, long userId) {
    if (!paidExpenses.containsKey(expenseId)) return;
    paidExpenses.get(expenseId).remove(userId);
  }

  /**
   * Returns true if the expense is paid by the user.
   *
   * @param   expenseId the expense ID
   * @param   userId the user iD
   * @return  true if the expense is paid by the user
   */
  public boolean isPaid(long expenseId, long userId) {
    return paidExpenses.getOrDefault(expenseId, new ArrayList<>()).contains(userId);
  }

  /**
   * Returns a map of the paid expenses.
   *
   * @return  a map of the paid expenses
   */
  public Map<Long, List<Long>> getPaidExpenses() {
    return paidExpenses;
  }

  /**
   * Set the current group.
   *
   * @param   groupId the group ID
   */
  public static void setCurrent(long groupId) {
    CURRENT = groupId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Group group = (Group) o;
    return id == group.id && name.equals(group.name) && members.equals(group.members)
            && expenses.equals(group.expenses) && income.equals(group.income) && paidExpenses.equals(group.paidExpenses) && receivedIncome.equals(group.receivedIncome);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, members, expenses, income, paidExpenses, receivedIncome);
  }
}