package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.Group;

public interface GroupDAO {

    /**
     * Insert a group.
     * @param   group the group
     * @return  true if the group was added
     */
    boolean insert(Group group);

    /**
     * Finds a group with the given ID.
     * @param   id the id
     * @return  a group with the given ID
     */
    Group find(long groupId);

    /**
     * Returns the group of this user.
     * @param   id the user id
     * @return  the group of this user
     */
    Group findByUser(long userId);
}