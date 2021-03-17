package org.deleted.bots.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.deleted.bots.annotation.Configuration;
import org.deleted.bots.annotation.Inject;
import org.deleted.bots.annotation.PostStart;
import org.deleted.bots.core.MessageEventHandle;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * 与mirai机器人的WebSocket链接在这里初始化
 */

@Configuration
public class WebSocket {

    private static final Logger logger = LogManager.getLogger(WebSocket.class);
    private WebSocketClient webSocketClient;

    @Inject
    MessageEventHandle handler;

    @PostStart
    public void init() {
        webSocketClient();
    }

    //用于websocket链接
    private String websocketUrl = "ws://" + System.getProperty("ip");

    private void dispatchEvent() {

    }

    public WebSocketClient webSocketClient() {
        try {
            //开始进行WebSocket连接 监听Mirai的消息事件。
            this.webSocketClient = new WebSocketClient(new URI(websocketUrl + "/message?sessionKey=" + System.getProperty("sessionKey")), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    logger.info("[websocket] 连接成功");
                }

                @Override
                public void onMessage(String message) {
                    JSONObject messageJson = JSON.parseObject(message);
                    String type = (String) messageJson.get("type");
                    logger.debug("[websocket] 收到消息=" + message);
                    try {
                        if (type.equals("FriendMessage") || type.equals("TempMessage")) {
                            handler.privateMessageHandle(messageJson);
                        } else if (type.equals("GroupMessage")) {
                            handler.groupMessageHandle(messageJson);
                        }
                    } catch (Exception e) {
                        logger.error("handle message failed:",e);
                    }

                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    logger.warn(String.format("[websocket] %s退出连接: [%s] %s",remote ? "api server" : "local",code,reason));
                }

                @Override
                public void onError(Exception ex) {
                    logger.error("[websocket] 连接错误:",ex);
                }

            };
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
