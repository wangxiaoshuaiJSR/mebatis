package com.self.mebatis.framework.plugin;

/**
 * 拦截器接口，所有自定义拦截器必须实现此接口
 * Created by wangxiaoshuai on 2019/5/17.
 */
public interface Interceptor {

    /**
     * 插件的核心实现逻辑
     * @param invocation
     * @return
     * @throws Throwable
     */
    Object intercept(Invocation invocation)throws Throwable;

    /**
     * 对被拦截对象进行代理
     * @param target
     * @return
     */
    Object plugin(Object target);

}
