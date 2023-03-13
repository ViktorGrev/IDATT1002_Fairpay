package no.ntnu.idatt1002.demo.data.user;

import java.util.Date;

public final class Member {

    private final long userId;
    private final Date joinDate;

    public Member(long userId, Date joinDate) {
        if(userId < 0)
            throw new IllegalArgumentException("User id cannot be negative");
        if(joinDate == null)
            throw new IllegalArgumentException("Join date cannot be null");
        this.userId = userId;
        this.joinDate = joinDate;
    }

    public long getUserId() {
        return userId;
    }

    public Date getJoinDate() {
        return joinDate;
    }
}
