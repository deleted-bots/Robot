package org.deleted.bots.entity;

import java.io.Serializable;
import java.util.List;

public class PrivateMessageEvent extends MessageEvent implements Serializable {

    private String type;//消息类型
    private List<MessageChain> messageChain;//消息组合对象
    private String rawMessage;//文本化的消息对象
    private String messageId;//消息id
    private Sender sender;//发送者信息
    private Long userId;

    public PrivateMessageEvent(String type, List<MessageChain> messageChain, String rawMessage, String messageId, Sender sender) {
        this.type = type;
        this.messageChain = messageChain;
        this.rawMessage = rawMessage;
        this.messageId = messageId;
        this.sender = sender;
        this.userId = sender.getId();
    }

    public Long getUserId() {
        return userId;
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
