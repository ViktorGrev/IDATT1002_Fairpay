package no.ntnu.idatt1002.demo.data;

import no.ntnu.idatt1002.demo.data.person.Member;

import java.util.HashMap;

public class Group {
  HashMap<Member.getId(),Member> gruop = new HashMap<Member.getId(),Member>();
  private int groupId;
  private String groupName;


  public Group(int groupId, String groupName) {
    this.groupId = groupId;
    this.groupName = groupName;
  }

  public boolean addMember(Member member) {
    if (group.books.entrySet().stream()
            .filter(e -> (member.getId).equals(e.getValue()))
            .map(group.Entry::getKey)
            .findFirst()){
      return false;
    } else {
      group.put(member.getId(), member);
      return true;
    }
  }

  public boolean removeMember(Member member) {
    if(group.books.entrySet().stream()
            .filter(e -> (member.getId).equals(e.getValue()))
            .map(group.Entry::getKey)
            .findFirst()){
      group.remove(member.getId());
      return true;
    } else {
      return false;
    }
  }

  public int getGroupId() {
    return groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }
}
