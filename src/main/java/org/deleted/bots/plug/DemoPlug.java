package org.deleted.bots.plug;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deleted.bots.annotation.Inject;
import org.deleted.bots.annotation.QQMsgHandler;
import org.deleted.bots.core.Mirai;
import org.deleted.bots.entity.GroupMessageEvent;
import org.deleted.bots.entity.MemberEvent;
import org.deleted.bots.entity.PrivateMessageEvent;
import org.deleted.bots.util.Type;

import static org.deleted.bots.core.MiraiPlug.MESSAGE_IGNORE;

@QQMsgHandler
public class DemoPlug {

    private static Logger logger = LogManager.getLogger(DemoPlug.class);

    @Inject
    private Mirai mirai;

    @QQMsgHandler(Type.FRIEND_MESSAGE)
    public boolean onPrivateMessage(PrivateMessageEvent event) throws Exception{
        String message = event.getRawMessage();
        if (message.equals("ping")){
            mirai.sendPrivateMsg(event.getUserId(),"pong");
        }
        return MESSAGE_IGNORE;
    }

    @QQMsgHandler(Type.GROUP_MESSAGE)
    public boolean onGroupMessage(GroupMessageEvent event) throws Exception {
        String message = event.getRawMessage();
        if (message.equals("ping")){
            mirai.sendGroupMsg(event.getGroupId(),"pong");
        }
        return MESSAGE_IGNORE;
    }

    @QQMsgHandler(Type.TEMP_MESSAGE)
    public boolean onTempMessage(GroupMessageEvent event)throws Exception {
        String message = event.getRawMessage();
        logger.info(JSONObject.toJSONString(event));
        if (message.equals("ping")){
            mirai.sendTempMsg(event.getUserId(),event.getGroupId(),"pong");
        }
        return MESSAGE_IGNORE;
    }

    @QQMsgHandler(Type.MEMBER_LEAVE_EVENT_QUIT)
    public boolean onMeberLeave(MemberEvent event)throws Exception {
        Long qq = event.getMember().getId();
        Long group = event.getMember().getGroup().getId();
        mirai.sendGroupMsg(group,qq+"退出了本群");
        logger.info(JSONObject.toJSONString(event));
        return MESSAGE_IGNORE;
    }
}
