package no.ntnu.idatt1002.controller;

import no.ntnu.idatt1002.dao.*;
import no.ntnu.idatt1002.data.User;

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

    private final Map<Long, User> userCache = new HashMap<>();

    /**
     * This method attempts to find a local copy of a user
     * in the cache with the specified ID. If no user is
     * found, it will attempt to retrieve the user through
     * the {@link UserDAO} and store it as a local copy.
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
        for(User user : userDAO.find(fetch)) {
            users.add(user);
            userCache.put(user.getId(), user);
        }
        return users;
    }
}
