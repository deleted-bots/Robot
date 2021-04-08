package org.deleted.bots.entity;

import java.io.Serializable;

public class Member implements Serializable {

    private Long id;
    private String memberName;
    private String permission;
    private Group group;

    public Member(Long id, String memberName, String permission, Group group) {
        this.id = id;
        this.memberName = memberName;
        this.permission = permission;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
