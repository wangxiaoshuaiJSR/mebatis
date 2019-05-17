package com.self.mebatis.framework.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器链
 * Created by wangxiaoshuai on 2019/5/17.
 */
public class InterceptorChain {
    /**
     * 储存
     */
    private final List<Interceptor> interceptors = new ArrayList<>();

    public void addInterceptor(Interceptor interceptor){
        interceptors.add(interceptor);
    }

    /**
     * 对list里的东西进行代理
     */
    public Object pluginAll(Object target){
        for (Interceptor interceptor : interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    public boolean hasPlugin(){
        if(interceptors.size()==0){
            return false;
        }
        return true;
    }
}
