package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.Group;
import no.ntnu.idatt1002.data.User;

import java.util.Collection;
import java.util.List;

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
    Group find(long id);
}