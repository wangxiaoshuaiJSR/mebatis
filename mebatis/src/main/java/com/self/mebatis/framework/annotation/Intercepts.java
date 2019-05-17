package com.self.mebatis.framework.annotation;

import java.lang.annotation.*;

/**
 * 定义插件，插件实际上就是一个拦截器
 * Created by wangxiaoshuai on 2019/5/8.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Intercepts {
    String value();
}
