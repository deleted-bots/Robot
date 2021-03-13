package org.deleted.bots.core;

import com.alibaba.fastjson.JSONObject;
import org.deleted.bots.annotation.Initialization;
import org.deleted.bots.entity.MessageChain;
import org.deleted.bots.util.MessageUtil;
import org.deleted.bots.util.OkHttpClientUtil;

import java.io.IOException;
import java.util.List;


@Initialization
public class Mirai {

    private String httpUrl = "http://"+System.getProperty("ip");

    private OkHttpClientUtil client = OkHttpClientUtil.getInstance();

    public Long sendPrivateMsg(Long targetId,String context) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(targetId,context);
        JSONObject result =  client.postJsonObject(httpUrl+"/sendFriendMessage",jsonObject.toJSONString());
        System.out.println(result);
        return 1L;
    }
    public Long sendPrivateMsg(Long targetId, List<MessageChain> messageChains) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(targetId,messageChains);
        JSONObject result =  client.postJsonObject(httpUrl+"/sendFriendMessage",jsonObject.toJSONString());
        System.out.println(result);
        return 1L;
    }


    public Long sendGroupMsg(Long targetId,String context) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(targetId,context);
        JSONObject result =  client.postJsonObject(httpUrl+"/sendFriendMessage",jsonObject.toJSONString());
        System.out.println(result);
        return 1L;
    }

    public Long sendGroupMsg(Long targetId, List<MessageChain> messageChains) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(targetId,messageChains);
        JSONObject result =  client.postJsonObject(httpUrl+"/sendGroupMessage",jsonObject.toJSONString());
        System.out.println(result);
        return 1L;
    }
}
