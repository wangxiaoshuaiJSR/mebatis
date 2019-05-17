package com.self.mebatis.framework.plugin;

import com.self.mebatis.framework.annotation.Intercepts;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 用来代理执行器Executor
 * Created by wangxiaoshuai on 2019/5/17.
 */
public class Plugin implements InvocationHandler {
    private Object target;//被代理对象
    private Interceptor interceptor;//拦截器插件

    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plugin plugin = (Plugin) o;

        if (target != null ? !target.equals(plugin.target) : plugin.target != null) return false;
        return interceptor != null ? interceptor.equals(plugin.interceptor) : plugin.interceptor == null;
    }

    @Override
    public int hashCode() {
        int result = target != null ? target.hashCode() : 0;
        result = 31 * result + (interceptor != null ? interceptor.hashCode() : 0);
        return result;
    }

    /**
     * 对被代理对象进行代理，返回代理类
     * @return

     */
    public static Object wrap(Object object,Interceptor interceptor){
        Class clazz = object.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),new Plugin(object,interceptor));
    }

    /**
     *
     * @param proxy 代理类
     * @param method 被代理类的方法  被代理类的每个方法都会到这儿
     * @param args 被代理类的方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //自定义的插件上有@Intercepts注解，指定了拦截器的方法
        if(interceptor.getClass().isAnnotationPresent(Intercepts.class)){
            //如果是被拦截的方法，则进入自定义拦截器的逻辑
            if(method.getName().equals(interceptor.getClass().getAnnotation(Intercepts.class).value())){
                return interceptor.intercept(new Invocation(target,method,args));
            }
        }
        //非被拦截的方法，执行原有逻辑
        return method.invoke(target,method,args);
    }
}
