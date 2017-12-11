package com.eazy.commons.auth;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
/**
 * 自定义注解
 */
public @interface AuthPassport {

    boolean validate() default true;
}
