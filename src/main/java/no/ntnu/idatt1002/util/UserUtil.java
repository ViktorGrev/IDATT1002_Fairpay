package no.ntnu.idatt1002.util;

import no.ntnu.idatt1002.data.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A utility class for users.
 */
public final class UserUtil {

  /**
   * Maps the user ID to their user object from a list of users.
   *
   * @param   users the users to map
   * @return  a map of the user ID and their user object
   */
  public static Map<Long, User> toMap(List<User> users) {
    return users.stream().collect(Collectors.toMap(User::getId, user -> user));
  }
}
