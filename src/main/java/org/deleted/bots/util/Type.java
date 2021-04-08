package org.deleted.bots.util;

public enum Type {
    CLASS("Class"),
    FRIEND_MESSAGE("FriendMessage"),//好友消息
    GROUP_MESSAGE("GroupMessage"),//群消息
    TEMP_MESSAGE("TempMessage"),//临时会话消息
    MEMBER_LEAVE_EVENT_KICK("MemberLeaveEventKick"),//群成员被踢事件
    MEMBER_LEAVE_EVENT_QUIT("MemberLeaveEventQuit"),//群成员退群事件
    MEMBER_JOIN_EVENT("MemberJoinEvent");//新成员入群事件

    private String type;
    Type(String type) {
        this.type = type;
    }
    public String getType(){
        return type;
    }
}
