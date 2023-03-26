package no.ntnu.idatt1002.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a group of users. Each group has a unique
 * ID, but not necessarily a unique name.
 */
public final class Group {

  public static Group CURRENT; // The current group

  private final long groupId; // The ID of the group
  private String name; // The name of the group
  private final List<User> members = new ArrayList<>(); // A list with the users within this group

  /**
   Constructs a new group with the specified group ID and group name.
   * @param   groupId the ID of the group
   * @param   name the name of the group
   */
  public Group(long groupId, String name) {
    this.groupId = groupId;
    this.name = name;
  }

  /**
   * Returns the ID of the group.
   * @return  the ID of the group
   */
  public long getId() {
    return groupId;
  }

  /**
   * Sets the name of the group.
   * @param   name the new name of the group
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Returns the name of the group.
   * @return  the name of the group
   */
  public String getName() {
    return name;
  }

  /**
   * Adds a member to the group.
   * @param   user the user to add
   * @return  true if the member was successfully added, otherwise false
   */
  public boolean addMember(User user) {
    if(isMember(user.getId())) return false;
    members.add(user);
    return true;
  }

  /**
   * Removes a member from the group.
   * @param   user the user to remove
   * @return  true if the member was successfully removed, otherwise false
   */
  public boolean removeMember(User user) {
      return members.removeIf(member -> member.getId() == user.getId());
  }

  /**
   * Returns true if a member of this group has the specified ID.
   * @param   userId the ID of the user
   * @return  true if a member of this group has the specified ID
   */
  public boolean isMember(long userId) {
    return members.stream().anyMatch(user -> user.getId() == userId);
  }

  /**
   * Returns an unmodifiable list of the members of this group.
   * @return  an unmodifiable list of the members of this group
   */
  public List<User> getMembers() {
    return Collections.unmodifiableList(members);
  }

  /**
   * Set the current group.
   * @param   group the group
   */
  public static void setCurrent(Group group) {
    CURRENT = group;
  }
}