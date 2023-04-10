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
     * Add a member to a group specified by their IDs.
     * @param   groupId the group ID
     * @param   userId the user ID
     */
    void addMember(long groupId, long userId);

    /**
     * Add an invitation to a group for a user with the specified ID.
     * @param   groupId the group ID
     * @param   senderId the sender user ID
     * @param   targetId the target user ID
     */
    void addInvite(long groupId, long senderId, long targetId);

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
