package no.ntnu.idatt1002.data.economy;

import java.util.*;

/**
 * The Settlement class represents a settlement with a name,
 * a unique ID, and a list of members and expenses.
 */
public class Settlement {

  private final long id;
  private final long userId;
  private String name;
  private final Date date;
  private final List<Long> expenses;
  private final List<Long> members;
  private boolean ended;

  /**
   * Constructs a settlement object with the specified values.
   *
   * @param id      the ID of the settlement
   * @param userId  the user who created the settlement
   * @param name    the name of the settlement
   * @param date    the creation date
   * @throws IllegalArgumentException if any of the following are true:
   * <ul>
   *   <li>id is invalid</li>
   *   <li>userId is invalid</li>
   *   <li>blank name</li>
   *   <li>date is null</li>
   * </ul>
   */
  public Settlement(long id, long userId, String name, Date date, boolean ended) {
    if (id < 0)
      throw new IllegalArgumentException("invalid ID");
    if (userId < 0)
      throw new IllegalArgumentException("invalid user ID");
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("name cannot be blank");
    if (date == null)
      throw new IllegalArgumentException("date is null");
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.date = date;
    this.ended = ended;
    this.members = new ArrayList<>();
    this.expenses = new ArrayList<>();
  }

  /**
   * Returns the ID of the settlement.
   *
   * @return  the ID of the settlement
   */
  public long getId() {
    return id;
  }

  /**
   * Returns the ID of the user who created the settlement.
   *
   * @return  the ID of the user who created the settlement
   */
  public long getUserId() {
    return userId;
  }

  /**
   * Set the name of the settlement.
   *
   * @param   name the new name
   * @throws  IllegalArgumentException if the new name is blank
   */
  public void setName(String name) {
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("name is null or blank");
    this.name = name;
  }

  /**
   * Returns the name of the settlement.
   *
   * @return the name of the settlement
   */
  public String getName() {
    return name;
  }

  /**
   * Adds an expense to the list of expenses.
   *
   * @param   expenseId the expense ID to be added
   * @return  true if the expense was added successfully, otherwise false
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
   * Return an unmodifiable list of expenses.
   *
   * @return  an unmodifiable list of expenses
   */
  public List<Long> getExpenses() {
    return Collections.unmodifiableList(expenses);
  }

  /**
   * Adds a member to the settlement.
   *
   * @param   userId the user ID
   */
  public void addMember(long userId) {
    members.add(userId);
  }

  /**
   * Removes a member from the settlement.
   *
   * @param   userId the user ID
   */
  public void removeMember(long userId) {
    members.remove(userId);
  }

  /**
   * Returns an unmodifiable list of members.
   *
   * @return  an unmodifiable list of members
   */
  public List<Long> getMembers() {
    return Collections.unmodifiableList(members);
  }

  /**
   * Returns the date when the settlement was created.
   *
   * @return  the date when the settlement was created
   */
  public Date getDate() {
    return date;
  }

  /**
   * Change the settlement end status.
   *
   * @param   ended whether it is ended
   */
  public void setEnded(boolean ended) {
    this.ended = ended;
  }

  /**
   * Returns true if the settlement is ended, otherwise false.
   *
   * @return  true if the settlement is ended, otherwise false
   */
  public boolean isEnded() {
    return ended;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Settlement that = (Settlement) o;
    return id == that.id && userId == that.userId && ended == that.ended && Objects.equals(name, that.name)
            && Objects.equals(date, that.date) && Objects.equals(expenses, that.expenses)
            && Objects.equals(members, that.members);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, name, date, expenses, members, ended);
  }
}
