package org.deleted.bots.annotation;

import org.deleted.bots.util.Type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QQMsgHandler {
    public String[] type() default {};
    public Type value() default Type.CLASS;
}
