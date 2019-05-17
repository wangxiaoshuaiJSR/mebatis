package com.self.mebatis.framework.session;

/**
 * 解析配置文件，获取sqlSession
 * 该类必须为单例，它的作用就是创建sqlSession
 * Created by wangxiaoshuai on 2019/5/15.
 */
public class SqlSessionFactory {
    private  Configuration configuration;
    //包内友好，不允许使用者去new
    SqlSessionFactory(){
        System.out.println("加载了配置文件");
        configuration = new Configuration();
    }


    //
    public DefaultSqlSession openSqlSession(){
        return new DefaultSqlSession(configuration);
    }




}
