package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.data.User;

import java.util.Collection;
import java.util.List;

public interface UserDAO {

    /**
     * Insert a user.
     * @param   user the person
     * @return  true if the person was added
     */
    boolean insert(User user);

    /**
     * Finds a user with the given ID.
     * @param   id the id
     * @return  a user with the given ID or null if no one has the ID
     */
    User find(int id);

    /**
     * Finds a list of users with the given IDs.
     * @param   ids the ids
     * @return  a list of users with the given IDs.
     */
    List<User> find(Collection<Integer> ids);
}