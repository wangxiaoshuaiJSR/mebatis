package com.self.mebatis.framework.binding;

import com.self.mebatis.framework.session.DefaultSqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangxiaoshuai on 2019/5/15.
 */
public class MapperRegistry {

    //接口和工厂类的映射关系
    private final Map<Class<?>,MapperProxyFactory> mapperProxies = new HashMap<>();

    /**
     *
     * @param clazz  mapper类 接口
     * @param object  实体类，用来映射
     * @param <T>
     */
    public <T>void  addMapper(Class<?> clazz,Class<?> object){
        mapperProxies.put(clazz,new MapperProxyFactory(clazz,object));
    }

    public <T> T getMapper(Class<T> mapper, DefaultSqlSession sqlSession) {
        MapperProxyFactory proxyFactory = mapperProxies.get(mapper);
        if(proxyFactory==null){
            throw new RuntimeException("Type"+mapper+"can not find");
        }
        return (T) proxyFactory.newInstance(sqlSession);
    }
}
