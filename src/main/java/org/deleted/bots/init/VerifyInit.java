package org.deleted.bots.init;

import com.alibaba.fastjson.JSONObject;
import org.deleted.bots.annotation.Initialization;
import org.deleted.bots.annotation.PostStart;
import org.deleted.bots.until.OkHttpClientUtil;

/**
 * 初始系统中的session
 */
@Initialization
public class VerifyInit {
    //用于http请求
    private String httpUrl = "http://"+System.getProperty("ip");

    @PostStart
    public void init() throws Exception {
        String authKey = System.getProperty("authKey");
        auth(authKey);
    }

    private void getSession(String authKey)throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("authKey",authKey);
        OkHttpClientUtil client = new OkHttpClientUtil();
        JSONObject result =  client.postJsonObject(httpUrl+"/auth",jsonObject.toJSONString());
        int code = result.getInteger("code");
        if(code ==0){
            System.setProperty("sessionKey",result.getString("session"));
        }else {
            throw  new Exception(result.getString("msg"));
        }
    }
    private int auth(String authKey) throws Exception {
        getSession(authKey);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionKey",System.getProperty("sessionKey"));
        jsonObject.put("qq",System.getProperty("qq"));
        OkHttpClientUtil client = new OkHttpClientUtil();
        JSONObject result =  client.postJsonObject(httpUrl+"/verify",jsonObject.toJSONString());
        int code = result.getInteger("code");
        if (code ==0){
            return code;
        }else {
            throw  new Exception(result.getString("msg"));
        }
    }
}
