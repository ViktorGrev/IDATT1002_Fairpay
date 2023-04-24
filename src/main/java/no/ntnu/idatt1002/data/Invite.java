package no.ntnu.idatt1002.data;

import java.util.Date;
import java.util.Objects;

/**
 * Represents an invitation a user can receive from a group.
 */
public final class Invite {

  private final long groupId; // The ID of the group this invite applies to
  private final long senderId; // The ID of the user sending the invite
  private final long targetId; // The ID of the user receiving the invite
  private final Date sendDate; // The date when the invite was sent

  /**
   * Constructs an invitation from a group to a user.
   *
   * @param   groupId the group ID
   * @param   senderId the sender user ID
   * @param   targetId the target user ID
   */
  public Invite(long groupId, long senderId, long targetId, Date sendDate) {
    this.groupId = groupId;
    this.senderId = senderId;
    this.targetId = targetId;
    this.sendDate = sendDate;
  }

  /**
   * Returns the ID of the group that this invitation is associated with.
   *
   * @return  the ID of the group that this invitation is associated with
   */
  public long getGroupId() {
    return groupId;
  }

  /**
   * Returns the ID of the user who sent the invitation.
   *
   * @return  the ID of the user who sent the invitation
   */
  public long getSenderId() {
    return senderId;
  }

  /**
   * Returns the ID of the user who is receiving the invitation.
   *
   * @return  the ID of the user who is receiving the invitation
   */
  public long getTargetId() {
    return targetId;
  }

  /**
   * Returns the date when this invitation was sent.
   *
   * @return  the date when this invitation was sent
   */
  public Date getDate() {
    return sendDate;
  }
}
