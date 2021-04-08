package org.deleted.bots.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deleted.bots.annotation.Initialization;
import org.deleted.bots.annotation.Inject;
import org.deleted.bots.annotation.QQMsgHandler;
import org.deleted.bots.entity.MemberEvent;
import org.deleted.bots.entity.MessageEvent;
import org.deleted.bots.init.Context;
import org.deleted.bots.util.Type;

import java.lang.reflect.Method;

@Initialization
public class BotEventHandle {

    private static final Logger logger = LogManager.getLogger(MessageEventHandle.class);

    @Inject
    private Context ctx;

    private <T extends MessageEvent> void invokeMessage(Enum type, Object obj, T event) {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(QQMsgHandler.class)) {
                Enum mType = method.getAnnotation(QQMsgHandler.class).value();
                if (mType.equals(type)) {
                    try {
                        obj.getClass()
                                .getDeclaredMethod(method.getName(), event.getClass())
                                .invoke(obj, event);
                    } catch (Exception e) {
                        logger.error("invoke message handler failed:", e);
                    }
                }
            }
        }
    }

    public void memberLeaveEventQuit(JSONObject message){
        MemberEvent event = JSON.parseObject(String.valueOf(message), MemberEvent.class);
        for(Object obj : ctx.getPlugins()){
            invokeMessage(Type.MEMBER_LEAVE_EVENT_QUIT,obj,event);
        }
    }

}
