package com.self.mebatis.framework.binding;

import com.self.mebatis.framework.session.DefaultSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * MapperProxy 代理类，用于代理Mapper接口
 * Created by wangxiaoshuai on 2019/5/16.
 */
public class MapperProxy implements InvocationHandler{
    private DefaultSqlSession sqlSession;
    private Class<?> object;

    public MapperProxy(DefaultSqlSession sqlSession, Class<?> object) {
        this.sqlSession = sqlSession;
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //真正调用SqlSession里的方法，通过sqlSession调用Executor里的方法
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();
        String key = className+"."+methodName;
        //method是否有对应的sql语句
        if(sqlSession.getConfiguration().hasStatement(key)){
            return sqlSession.selectOne(key,args,object);
        }
        return method.invoke(proxy,args);
    }
}
