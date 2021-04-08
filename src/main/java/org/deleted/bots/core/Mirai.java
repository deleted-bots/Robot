package org.deleted.bots.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deleted.bots.annotation.Initialization;
import org.deleted.bots.entity.MessageChain;
import org.deleted.bots.util.MessageUtil;
import org.deleted.bots.util.OkHttpClientUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Initialization
public class Mirai {
    private static final Logger logger = LogManager.getLogger(Mirai.class);

    private String httpUrl = "http://"+System.getProperty("ip");

    private OkHttpClientUtil client = OkHttpClientUtil.getInstance();

    /**
     * 发送好友消息
     * @param targetId 接收者qq
     * @param context 消息文本
     * @return
     * @throws IOException
     */
    public Long sendPrivateMsg(Long targetId,String context) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(targetId,context);
        requestMiraiAPI("/sendFriendMessage",jsonObject.toJSONString());
        return 1L;
    }

    /**
     * 发送好友消息
     * @param targetId 接收者qq
     * @param messageChains 消息链集合
     * @return
     * @throws IOException
     */
    public Long sendPrivateMsg(Long targetId, List<MessageChain> messageChains) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(targetId,messageChains);
        requestMiraiAPI("/sendFriendMessage",jsonObject.toJSONString());
        return 1L;
    }

    /**
     * 发送群消息
     * @param targetId 群号
     * @param context 消息文本
     * @return
     * @throws IOException
     */
    public Long sendGroupMsg(Long targetId,String context) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(targetId,context);
        requestMiraiAPI("/sendGroupMessage",jsonObject.toJSONString());
        return 1L;
    }

    /**
     * 发送群消息
     * @param targetId 群号
     * @param messageChains 消息链集合
     * @return
     * @throws IOException
     */
    public Long sendGroupMsg(Long targetId, List<MessageChain> messageChains) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(targetId,messageChains);
        requestMiraiAPI("/sendGroupMessage",jsonObject.toJSONString());
        return 1L;
    }

    /**
     * 发送群临时会话消息
     * @param qq 接收者的qq
     * @param groupId 接收者所在群
     * @param context 消息文本
     * @return
     * @throws IOException
     */
    public Long sendTempMsg(Long qq,Long groupId,String context) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(qq,groupId,context);
        requestMiraiAPI("/sendTempMessage",jsonObject.toJSONString());
        return 1L;
    }

    /**
     * 发送群临时会话消息
     * @param qq 接收者的qq
     * @param groupId 接收者所在群
     * @param messageChains 消息链集合
     * @return
     * @throws IOException
     */
    public Long sendTempMsg(Long qq,Long groupId, List<MessageChain> messageChains) throws IOException {
        JSONObject jsonObject = MessageUtil.sendMessageAssemble(qq,groupId,messageChains);
        requestMiraiAPI("/sendTempMessage",jsonObject.toJSONString());
        return 1L;
    }

    /**
     * 撤回消息
     * @param messageId 需要撤回消息的 messageId
     * @return
     * @throws IOException
     */
    public Long recall(String messageId) throws IOException {
        Map<String,String> body = new HashMap<String,String>();
        body.put("target",messageId);
        //通过此方法获取一个带有sessionKey的json对象
        JSONObject jsonObject = MessageUtil.requestBodyAssemble(body);
        requestMiraiAPI("/recall",jsonObject.toJSONString());
        return 1L;
    }

    /**
     * 请求 Mirai http api 的统一入口
     * @param path API路径
     * @param jsonString  请求的报文
     * @return 请求后返回的结果集
     * @throws IOException
     */
    public JSONObject requestMiraiAPI(String path,String jsonString) throws IOException {
        logger.debug("[Mirai API request]"+jsonString);
        JSONObject result =  client.postJsonObject(httpUrl+path,jsonString);
        logger.debug("[Mirai API response]"+result);
        return result;
    }
}
