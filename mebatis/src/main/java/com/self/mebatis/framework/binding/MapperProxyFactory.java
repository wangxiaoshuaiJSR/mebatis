package com.self.mebatis.framework.binding;

import com.self.mebatis.framework.session.DefaultSqlSession;

import java.lang.reflect.Proxy;

/**
 * Created by wangxiaoshuai on 2019/5/15.
 */
public class MapperProxyFactory<T> {
    public Class<T> mapperInterface;
    private Class<?> object;

    public MapperProxyFactory(Class<T> mapperInterface, Class<?> object) {
        this.mapperInterface = mapperInterface;
        this.object = object;
    }

    //创建代理类
    public T newInstance(DefaultSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(),new Class[]{mapperInterface},new MapperProxy(sqlSession,object));
    }
}
