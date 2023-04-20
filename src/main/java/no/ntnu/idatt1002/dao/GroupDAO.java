package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.Invite;

import java.util.List;

/**
 * This interface defines methods to interact with groups and group related tasks.
 */
public interface GroupDAO extends DAO<Group, Long> {

    /**
     * Create a new group with the specified name.
     * @param   name the name of the group
     * @return  the new group
     */
    Group create(String name);

    /**
     * Add a member to a group specified by their ID.
     * @param   groupId the group ID
     * @param   userId the user ID
     */
    void addMember(long groupId, long userId);

    /**
     * Remove a member to a group specified by their ID.
     * @param   groupId the group ID
     * @param   userId the user ID
     */
    void removeMember(long groupId, long userId);

    /**
     * Add an expense to a group.
     * @param   groupId the group ID
     * @param   expenseId the user ID
     */
    void addExpense(long groupId, long expenseId);

    /**
     * Add an income to a group.
     * @param   groupId the group ID
     * @param   incomeId the income ID
     */
    void addIncome(long groupId, long incomeId);

    /**
     * Sets the status of an income to received.
     * @param   incomeId the income ID
     * @param   userId the user ID
     */
    void setReceivedIncome(long incomeId, long userId);

    /**
     * Unsets the status of an income.
     * @param   incomeId the income ID
     * @param   userId the user ID
     */
    void unsetReceivedIncome(long incomeId, long userId);

    /**
     * Sets the status of an expense to paid.
     * @param   expenseId the expense ID
     * @param   userId the user ID
     */
    void setPaidExpense(long expenseId, long userId);

    /**
     * Unsets the status of an expense.
     * @param   expenseId the income ID
     * @param   userId the user ID
     */
    void unsetPaidExpense(long expenseId, long userId);

    /**
     * Add an invitation to a group for a user with the specified ID.
     * @param   groupId the group ID
     * @param   senderId the sender user ID
     * @param   targetId the target user ID
     */
    Invite addInvite(long groupId, long senderId, long targetId);

    /**
     * Removes an invitation from a group.
     * @param   groupId the group ID
     * @param   userId the user ID
     */
    void removeInvite(long groupId, long userId);

    /**
     * Returns an unmodifiable list of invites for a group
     * with the specified ID.
     * @param   groupId the group ID
     * @return  an unmodifiable list of invites for a user
     */
    List<Invite> getInvites(long groupId);

    /**
     * Returns an unmodifiable list of invites for a user
     * with the specified ID.
     * @param   userId the user ID
     * @return  an unmodifiable list of invites for a user
     */
    List<Invite> getInvitesByUser(long userId);

    /**
     * Returns the group of this user.
     * @param   userId the user id
     * @return  the group of this user
     */
    Group findByUser(long userId);
}
