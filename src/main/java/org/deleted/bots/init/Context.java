package org.deleted.bots.init;

import org.deleted.bots.annotation.Internal;
import org.deleted.bots.core.Mirai;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Internal
public class Context{
    private Map<String,Object> map = new HashMap<>();
    private Map<String,Object> plugins = new HashMap<>();

    public Context(){
        map.put(Context.class.getSimpleName(),this);
    }

    public Object get(String key){
        return map.get(key);
    }

    public Collection<Object> getPlugins(){
        return plugins.values();
    }

    public void putPlugin(String key,Object obj){
        plugins.put(key,obj);
    }

    public void put(String key,Object obj){
        map.put(key,obj);
    }
}
