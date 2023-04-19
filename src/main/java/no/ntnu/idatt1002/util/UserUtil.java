package no.ntnu.idatt1002.util;

import no.ntnu.idatt1002.data.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class UserUtil {

    public static Map<Long, User> toMap(List<User> users) {
        return users.stream().collect(Collectors.toMap(User::getId, user -> user));
    }
}
