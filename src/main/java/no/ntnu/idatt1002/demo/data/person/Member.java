package no.ntnu.idatt1002.demo.data.person;

import java.util.Date;

public final class Member {

    private final long personId;
    private final Date joinDate;

    public Member(long personId, Date joinDate) {
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
