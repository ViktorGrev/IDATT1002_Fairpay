package no.ntnu.idatt1002.dao;

import no.ntnu.idatt1002.dao.exception.AuthException;
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
    User find(long id);

    /**
     * Finds a list of users with the given IDs.
     * @param   ids the ids
     * @return  a list of users with the given IDs.
     */
    List<User> find(Collection<Long> ids);

    /**
     * Authenticates a user with a given username and password.
     * @param   username the username
     * @param   password the password
     * @return  the id of the user with the specified username and password
     * @throws  AuthException if the password does not match or the user is not found
     */
    long authenticate(String username, String password) throws AuthException;
}