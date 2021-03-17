package org.deleted.bots.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deleted.bots.annotation.Initialization;
import org.deleted.bots.annotation.Inject;
import org.deleted.bots.annotation.QQMsgHandler;
import org.deleted.bots.entity.GroupMessageEvent;
import org.deleted.bots.entity.MessageEvent;
import org.deleted.bots.entity.PrivateMessageEvent;
import org.deleted.bots.init.Context;
import org.deleted.bots.util.MessageUtil;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 处理有关群消息的事件触发
 */
@Initialization
public class MessageEventHandle {

    private static final Logger logger = LogManager.getLogger(MessageEventHandle.class);

    @Inject
    private Context ctx;

    private <T extends MessageEvent> void invokeMessage(String type, Object obj, T event) {
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(QQMsgHandler.class)) {
                String[] types = method.getAnnotation(QQMsgHandler.class).type();
                if (Arrays.asList(types).contains(type)) {
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

    public void groupMessageHandle(JSONObject message) {
        GroupMessageEvent messageEvent = MessageUtil.GmessageEventAssemble(message);
        for (Object obj : ctx.getPlugins()) {
            invokeMessage("Group", obj, messageEvent);
        }

    }

    public void privateMessageHandle(JSONObject message) {
        PrivateMessageEvent messageEvent = MessageUtil.PmessageEventAssemble(message);
        for (Object obj : ctx.getPlugins()) {
            invokeMessage("Private", obj, messageEvent);
        }
    }


}
