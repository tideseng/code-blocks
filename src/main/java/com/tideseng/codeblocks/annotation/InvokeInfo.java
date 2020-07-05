package com.tideseng.codeblocks.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 注解类
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface InvokeInfo {

    /**
     * 描述信息
     * @return
     */
    @AliasFor("desc")
    String value() default "";

    @AliasFor("value")
    String desc() default "";

}
