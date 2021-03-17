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
        jsonObject.put("messageChain",textToMessageChain(context));
        return jsonObject;
    }
    public static JSONObject sendMessageAssemble(Long id,List<MessageChain> messageChains){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey",System.getProperty("sessionKey"));
        jsonObject.put("target",id);
        jsonObject.put("messageChain",messageChains);
        return jsonObject;
    }

    /**
     * 临时会话组织的消息格式
     * 需要同时指定 qq号 和 群号
     * @param qq  接受者的qq
     * @param groupId  接受者所在群
     * @param context 消息集合本文化
     * @return
     */
    public static JSONObject sendMessageAssemble(Long qq,Long groupId,String context){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey",System.getProperty("sessionKey"));
        jsonObject.put("qq",qq);
        jsonObject.put("group",groupId);
        jsonObject.put("messageChain",textToMessageChain(context));
        return jsonObject;
    }

    /**
     * 临时会话组织的消息格式
     * 需要同时指定 qq号 和 群号
     * @param qq  接受者的qq
     * @param groupId  接受者所在群
     * @param messageChains 完整的消息集合
     * @return
     */
    public static JSONObject sendMessageAssemble(Long qq,Long groupId,List<MessageChain> messageChains){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey",System.getProperty("sessionKey"));
        jsonObject.put("qq",qq);
        jsonObject.put("group",groupId);
        jsonObject.put("messageChain",messageChains);
        return jsonObject;
    }

    /**
     * 将文本封装成 textToMessageChain
     * @param text 要封装的文本
     * @return 已经转成 JSONArray的消息集合
     */
    public static JSONArray textToMessageChain(String text){
        JSONArray array = new JSONArray();
        MessageChain messageChain = new MessageChain();
        messageChain.setType("Plain");
        messageChain.setText(text);
        array.add(messageChain);
        return array;
    }
}
