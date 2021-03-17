package org.deleted.bots.core;

import org.deleted.bots.annotation.Plug;
import org.deleted.bots.entity.Group;
import org.deleted.bots.entity.GroupMessageEvent;
import org.deleted.bots.entity.PrivateMessageEvent;

/**
 * 机器人插件的抽象类，所有插件必须继承此类才能够被事件处理类识别
 */
@Plug
public abstract class MiraiPlug {
    public static boolean MESSAGE_IGNORE = true;
    public static boolean MESSAGE_BLOCK = false;
    public abstract  boolean onPrivateMessage(Mirai mirai, PrivateMessageEvent event) throws Exception;
    public abstract  boolean onGroupMessage(Mirai mirai, GroupMessageEvent event) throws Exception;
    public abstract  boolean onTempMessage(Mirai mirai, GroupMessageEvent event)throws Exception;
}
