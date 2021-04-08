package org.deleted.bots.entity;

/**
 * 支持的事件范围 新人入群的事件，成员被踢出群，成员主动离群
 */
public class MemberEvent extends MessageEvent {
    private String type;
    private Member member;
    private Operator operator;

    public MemberEvent(String type, Member member, Operator operator) {
        this.type = type;
        this.member = member;
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
