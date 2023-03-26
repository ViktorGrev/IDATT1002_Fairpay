package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.Group;

public interface GroupDAO {

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
     * @param   userId the user ID
     */
    void addInvite(long groupId, long userId);

    /**
     * Returns the group of this user.
     * @param   id the user id
     * @return  the group of this user
     */
    Group findByUser(long userId);
}