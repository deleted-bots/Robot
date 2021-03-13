package org.deleted.bots.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.deleted.bots.entity.*;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {

    public static PrivateMessageEvent PmessageEventAssemble(JSONObject message){
        String type = (String)message.get("type");
        String messageId = "";
        String rawMessage = "";
        String atTarget = "";
        List<MessageChain> messageChains = new ArrayList<MessageChain>();
        messageChains = JSON.parseArray(message.getString("messageChain"),MessageChain.class);
        for(MessageChain  m:messageChains){
            String itemType = m.getType();
            if(itemType.equals("Source")){//消息源
                messageId = m.getId();
            }else if(itemType.equals("Plain")){//文本消息
                rawMessage += m.getText();
            }else if(itemType.equals("Face")){//表情消息
                //暂时不处理表情
            }
        }
        Sender sender = JSON.parseObject(message.getString("sender"),Sender.class);
        return new PrivateMessageEvent(type,messageChains,rawMessage,messageId,sender);
    }

    public static GroupMessageEvent GmessageEventAssemble(JSONObject message){
        String type = (String)message.get("type");
        String messageId = "";
        String rawMessage = "";
        Long atTarget = -1L;
        List<MessageChain> messageChains = new ArrayList<MessageChain>();
        messageChains = JSON.parseArray(message.getString("messageChain"),MessageChain.class);
        for(MessageChain  m:messageChains){
            String itemType = m.getType();
            if(itemType.equals("Source")){//消息源
                messageId = m.getId();
            }else if(itemType.equals("Plain")){//文本消息
                rawMessage += m.getText();
            }else if(itemType.equals("Face")){//表情消息
                //暂时不处理表情
            }else if(itemType.equals("At")){//@消息
                atTarget =  Long.parseLong(m.getTarget());
            }
        }
        Sender sender = JSON.parseObject(message.getString("sender"),Sender.class);
        return new GroupMessageEvent(type,messageChains,rawMessage,messageId,sender,atTarget);
    }

    public static JSONObject sendMessageAssemble(Long id,String context){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey",System.getProperty("sessionKey"));
        jsonObject.put("target",id);
        JSONArray array = new JSONArray();
        MessageChain messageChain = new MessageChain();
        messageChain.setType("Plain");
        messageChain.setText(context);
        array.add(messageChain);
        jsonObject.put("messageChain",array);
        return jsonObject;
    }
    public static JSONObject sendMessageAssemble(Long id,List<MessageChain> messageChains){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey",System.getProperty("sessionKey"));
        jsonObject.put("target",id);
        jsonObject.put("messageChain",messageChains);
        return jsonObject;
    }
}
