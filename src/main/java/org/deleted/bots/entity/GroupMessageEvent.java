package org.deleted.bots.entity;

import java.util.List;

public class GroupMessageEvent extends MessageEvent{
    private String type;//消息类型
    private List<MessageChain> messageChain;//消息组合对象
    private String rawMessage;//文本化的消息对象
    private String messageId;//消息id
    private Sender sender;//发送者信息
    private Long atTarget;
    private Long userId;
    private Long groupId;
    public GroupMessageEvent(String type, List<MessageChain> messageChain, String rawMessage, String messageId, Sender sender,Long atTarget) {
        this.type = type;
        this.messageChain = messageChain;
        this.rawMessage = rawMessage;
        this.messageId = messageId;
        this.sender = sender;
        this.userId = sender.getId();
        this.groupId = sender.getGroup().getId();
        this.atTarget = atTarget;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public Long getAtTarget() {
        return atTarget;
    }

    public void setAtTarget(Long atTarget) {
        this.atTarget = atTarget;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<MessageChain> getMessageChain() {
        return messageChain;
    }

    public void setMessageChain(List<MessageChain> messageChain) {
        this.messageChain = messageChain;
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
