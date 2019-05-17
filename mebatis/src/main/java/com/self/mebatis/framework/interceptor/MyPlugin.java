package com.self.mebatis.framework.interceptor;

import com.self.mebatis.framework.annotation.Intercepts;
import com.self.mebatis.framework.plugin.Interceptor;
import com.self.mebatis.framework.plugin.Invocation;
import com.self.mebatis.framework.plugin.Plugin;

import java.util.Arrays;

/**
 * 自定义插件  实际上插件是对Executor的代理包装
 * Created by wangxiaoshuai on 2019/5/17.
 */
@Intercepts("query")
public class MyPlugin implements Interceptor{
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String statement = (String) invocation.getArgs()[0];
        Object[] parameter = (Object[]) invocation.getArgs()[1];
        Class pojo = (Class) invocation.getArgs()[2];
        System.out.println("插件输出SQL["+statement+"]");
        System.out.println("插件输出：PARAMETERS"+ Arrays.toString(parameter));
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }
}
