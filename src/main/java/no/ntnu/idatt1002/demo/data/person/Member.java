package no.ntnu.idatt1002.demo.data.person;

import java.util.Date;

public final class Member {

    private final long personId;
    private final Date joinDate;

    public Member(long personId, Date joinDate) {
        if(personId < 0)
            throw new IllegalArgumentException("Person id cannot be negative");
        if(joinDate == null)
            throw new IllegalArgumentException("Join date cannot be null");
        this.personId = personId;
        this.joinDate = joinDate;
    }

    public long getPersonId() {
        return personId;
    }

    public Date getJoinDate() {
        return joinDate;
    }
}
