package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.data.person.Member;

import java.util.HashMap;

public class Group {
  HashMap<Long,Member> group = new HashMap<Long,Member>();
  private final int groupId;
  private String groupName;


  public Group(int groupId, String groupName) {
    this.groupId = groupId;
    this.groupName = groupName;
  }

  public boolean addMember(Member member) {
    if (group.containsKey(member.getPersonId())){
      return false;
    } else {
      group.put(member.getPersonId(), member);
      return true;
    }
  }

  public boolean removeMember(Member member) {
    if(group.containsKey(member.getPersonId())){
      group.remove(member.getPersonId());
      return true;
    } else {
      return false;
    }
  }

  public int getGroupId() {
    return groupId;
  }

  public String getGroupName() {
    return groupName;
  }

  public HashMap<Long, Member> getGroup() {
    return group;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
}
