package com.self.mebatis.framework.annotation;

import java.lang.annotation.*;

/**
 * 根据该注解读取mapper接口里方法上定义的sql语句
 * Created by wangxiaoshuai on 2019/5/8.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {
    String value();
}
