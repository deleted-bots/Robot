package org.deleted.bots.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Internal
@Target(ElementType.TYPE)//只在类上有作用
@Retention(RetentionPolicy.RUNTIME)//在什么时期起作用 运行期有效
public @interface Configuration {
}
