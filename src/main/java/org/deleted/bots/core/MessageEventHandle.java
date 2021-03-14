package org.deleted.bots.core;

import com.alibaba.fastjson.JSONObject;
import org.deleted.bots.annotation.QQMsgHandler;
import org.deleted.bots.entity.GroupMessageEvent;
import org.deleted.bots.entity.MessageEvent;
import org.deleted.bots.entity.PrivateMessageEvent;
import org.deleted.bots.init.Context;
import org.deleted.bots.util.MessageUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理有关群消息的事件触发
 */
public class MessageEventHandle {

    private <T extends MessageEvent> void  invokeMessage(String type, Object obj, T event){
        for(Method method : obj.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(QQMsgHandler.class)){
                String[] types = method.getAnnotation(QQMsgHandler.class).type();
                if(Arrays.asList(types).contains(type)){
                    try {
                        obj.getClass()
                                .getDeclaredMethod(method.getName(),event.getClass())
                                .invoke(obj,event);
                    } catch (Exception e) {
                        //todo logger
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void groupMessageHandle(JSONObject message){
        GroupMessageEvent messageEvent = MessageUtil.GmessageEventAssemble(message);
        for(Object obj : Context.getInstance().getPlugins()){
            invokeMessage("Group",obj,messageEvent);
        }

    }

    public void privateMessageHandle(JSONObject message){
        PrivateMessageEvent messageEvent = MessageUtil.PmessageEventAssemble(message);
        for(Object obj : Context.getInstance().getPlugins()){
            invokeMessage("Private",obj,messageEvent);
        }
    }

    public void tempMessageHandle(JSONObject message){
        //临时会话使用的数据格式与群消息一直所以也使用GroupMessageEvent
        GroupMessageEvent messageEvent = MessageUtil.GmessageEventAssemble(message);
        for(Object obj : Context.getInstance().getPlugins()){
            invokeMessage("Temp",obj,messageEvent);
        }

    }


}
