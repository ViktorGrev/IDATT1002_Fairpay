package no.ntnu.idatt1002.controller;

import no.ntnu.idatt1002.dao.*;
import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;
import no.ntnu.idatt1002.scene.Page;
import no.ntnu.idatt1002.scene.SceneSwitcher;

import java.util.*;

/**
 * This class defines commonly used methods and variables
 * in the controller classes.
 */
abstract class Controller {

  protected static final UserDAO userDAO = Database.getDAO(UserDAO.class);
  protected static final GroupDAO groupDAO = Database.getDAO(GroupDAO.class);
  protected static final ExpenseDAO expenseDAO = Database.getDAO(ExpenseDAO.class);
  protected static final SettlementDAO settlementDAO = Database.getDAO(SettlementDAO.class);
  protected static final BudgetDAO budgetDAO = Database.getDAO(BudgetDAO.class);
  protected static final IncomeDAO incomeDAO = Database.getDAO(IncomeDAO.class);

  private final Map<Long, User> userCache = new HashMap<>();
  private final Map<Long, Group> groupCache = new HashMap<>();

  /**
   * This method attempts to find a local copy of a user
   * in the cache with the specified ID. If no user is
   * found, it will attempt to retrieve the user through
   * the {@link UserDAO} and store it as a local copy.
   *
   * @param   id the ID of the user
   * @return  the user
   */
  protected User getUser(long id) {
    return userCache.computeIfAbsent(id, k -> userDAO.find(id));
  }

  /**
   * This method attempts to find a local copy of a user
   * in the cache with the specified username. If no user is
   * found, it will attempt to retrieve the user through
   * the {@link UserDAO} and store it as a local copy.
   *
   * @param   username the username of the user
   * @return  the user
   */
  protected User getUser(String username) {
    for(User temp : userCache.values()) {
      if(temp.getUsername().equalsIgnoreCase(username))
        return temp;
    }
    User user = userDAO.find(username);
    if(user == null) return null;
    userCache.put(user.getId(), user);
    return user;
  }

  /**
   * This method attempts to find local copies of
   * multiple users. If a user is not found, it will
   * perform a lookup using the {@link UserDAO} and
   * store the result as a local copy.
   *
   * @param   ids the user IDs
   * @return  the list of users
   */
  protected List<User> getUsers(Collection<Long> ids) {
    List<User> users = new ArrayList<>();
    Set<Long> fetch = new HashSet<>();
    for(long id : ids) {
      User local = userCache.get(id);
      if(local != null) {
        users.add(local);
      } else {
        fetch.add(id);
      }
    }
    if(!fetch.isEmpty()) {
      for(User user : userDAO.find(fetch)) {
        users.add(user);
        userCache.put(user.getId(), user);
      }
    }
    return users;
  }

  /**
   * This method attempts to find a local copy of a group
   * in the cache with the specified ID. If no group is
   * found, it will attempt to retrieve the user through
   * the {@link GroupDAO} and store it as a local copy.
   *
   * @param   id the ID of the group
   * @return  the group
   */
  protected Group getGroup(long id) {
    return groupCache.computeIfAbsent(id, k -> groupDAO.find(id));
  }

  /**
   * This method attempts to find local copies of
   * multiple groups. If a user is not found, it will
   * perform a lookup using the {@link GroupDAO} and
   * store the result as a local copy.
   *
   * @param   ids the group IDs
   * @return  the list of groups
   */
  protected List<Group> getGroups(Collection<Long> ids) {
    List<Group> groups = new ArrayList<>();
    Set<Long> fetch = new HashSet<>();
    for(long id : ids) {
      Group local = groupCache.get(id);
      if(local != null) {
        groups.add(local);
      } else {
        fetch.add(id);
      }
    }
    if(!fetch.isEmpty()) {
      for(Group group : groupDAO.find(fetch)) {
        groups.add(group);
        groupCache.put(group.getId(), group);
      }
    }
    return groups;
  }

  /**
   * Set the current page to the specified page.
   *
   * @param   page the page to view
   */
  protected void viewPage(Page page) {
    SceneSwitcher.setView(page);
  }
}
