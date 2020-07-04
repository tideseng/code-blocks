package com.tideseng.codeblocks.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface InvokeInfo {

    @AliasFor("desc")
    String value() default "";

    @AliasFor("value")
    String desc() default "";

}
