package no.ntnu.idatt1002.data;

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
    if (group.containsKey(member.getUserId())){
      return false;
    } else {
      group.put(member.getUserId(), member);
      return true;
    }
  }

  public boolean removeMember(Member member) {
    if(group.containsKey(member.getUserId())){
      group.remove(member.getUserId());
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
