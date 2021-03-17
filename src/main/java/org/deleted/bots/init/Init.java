package org.deleted.bots.init;

import org.deleted.bots.annotation.*;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * 初始化的总入口
 */
public class Init {

    private static final Logger logger = LogManager.getLogger(Init.class);

    private static void invokePostStart(Object obj) {
        for (Method method : obj.getClass().getMethods()) {
            if (method.isAnnotationPresent(PostStart.class)) {
                try {
                    method.invoke(obj);
                } catch (Exception e) {
                    logger.error(String.format(
                            "invoke post start %s.%s failed:",
                            obj.getClass().getSimpleName(),
                            method.getName()
                    ),e);
                }
                break;
            }
        }
    }

    private static void injectDependencies(Context ctx, Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Inject.class)) {
                if (!field.getType().isAnnotationPresent(Initialization.class) && !field.getType().isAnnotationPresent(Configuration.class)) {
                    logger.error(
                            String.format(
                                    "%s in %s.%s not processed by init",
                                    field.getType().getSimpleName(),
                                    obj.getClass().getSimpleName(),
                                    field.getName()
                            )

                    );
                }
                boolean canAccess = field.isAccessible();
                if (!canAccess) {
                    field.setAccessible(true);
                }
                try {
                    field.set(obj, ctx.get(field.getType().getSimpleName()));
                } catch (Exception e) {
                    logger.error(String.format("%s in %s.%s inject failed:",
                            field.getType().getSimpleName(),
                            obj.getClass().getSimpleName(),
                            field.getName()),e);
                }
                field.setAccessible(canAccess);
            }
        }
    }

    public static void init() throws Exception {
        Properties prop = new Properties();
        //加载配置文件中的配置属性

        prop.load(Init.class.getResourceAsStream("/robot.properties"));
        for (String key : prop.stringPropertyNames()) {
            System.setProperty(key, prop.getProperty(key));
        }
        //初始化功能 需要在链接websocket 之前完成动作可以在此初始化
        Reflections reflections = new Reflections(prop.getProperty("package"));//扫描指定包
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(Initialization.class);//获取使用了初始化注解的类
        Context ctx = new Context();
        for (Class<?> cls : classes) {
            Object o = cls.getDeclaredConstructor().newInstance();//通过反射创建对象
            invokePostStart(o);
            injectDependencies(ctx, o);
            ctx.put(cls.getSimpleName(), o);
        }
        //加载配置类
        classes = reflections.getTypesAnnotatedWith(Configuration.class);//获取使用了配置注解的类
        for (Class<?> cls : classes) {
            Object o = cls.getDeclaredConstructor().newInstance();//通过反射创建对象
            invokePostStart(o);
            ctx.put(cls.getSimpleName(), o);
        }
        classes = reflections.getTypesAnnotatedWith(QQMsgHandler.class);
        for (Class<?> cls : classes) {
            Object o = cls.getDeclaredConstructor().newInstance();//通过反射创建对象
            invokePostStart(o);
            //todo or not?dependencies analysis?
            injectDependencies(ctx, o);
            ctx.putPlugin(cls.getSimpleName(), o);
        }
    }
}
