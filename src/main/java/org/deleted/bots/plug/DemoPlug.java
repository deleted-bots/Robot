package org.deleted.bots.plug;

import org.deleted.bots.annotation.Inject;
import org.deleted.bots.annotation.QQMsgHandler;
import org.deleted.bots.core.Mirai;
import org.deleted.bots.entity.GroupMessageEvent;
import org.deleted.bots.entity.PrivateMessageEvent;

import static org.deleted.bots.core.MiraiPlug.MESSAGE_IGNORE;

@QQMsgHandler
public class DemoPlug {

    private boolean start = false;

    @Inject
    private Mirai mirai;

    @QQMsgHandler(type = {"Private"})
    public boolean onPrivateMessage(PrivateMessageEvent event) throws Exception{
        String message = event.getRawMessage();
        if (message.equals("ping")){
            mirai.sendPrivateMsg(event.getUserId(),"pong");
        }
        return MESSAGE_IGNORE;
    }

    @QQMsgHandler(type = {"Group"})
    public boolean onGroupMessage(GroupMessageEvent event) throws Exception {
        String message = event.getRawMessage();
        if (message.equals("ping")){
            mirai.sendGroupMsg(event.getGroupId(),"pong");
        }
        return MESSAGE_IGNORE;
    }

    public boolean onTempMessage(Mirai mirai, PrivateMessageEvent event) {
        return MESSAGE_IGNORE;
    }
}
