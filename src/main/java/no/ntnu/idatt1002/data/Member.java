package no.ntnu.idatt1002.data;

import java.util.Date;

/**
 * A class representing a member of a dorm group.
 */
public final class Member {

    private final long userId; // The unique id of the member
    private final Date joinDate; // The date when the member joined the organization

    /**
     * Constructs a new Member object with the specified user id and join date.
     *
     * @param userId the unique id of the member. Cannot be negative.
     * @param joinDate the date when the member joined the organization. Cannot be null.
     * @throws IllegalArgumentException if the user id is negative or if the join date is null.
     */
    public Member(long userId, Date joinDate) {
        if(userId < 0)
            throw new IllegalArgumentException("User id cannot be negative");
        if(joinDate == null)
            throw new IllegalArgumentException("Join date cannot be null");
        this.userId = userId;
        this.joinDate = joinDate;
    }

    /**
     * Returns the unique id of the member.
     *
     * @return the unique id of the member.
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Returns the date when the member joined the organization.
     *
     * @return the date when the member joined the organization.
     */
    public Date getJoinDate() {
        return joinDate;
    }
}
