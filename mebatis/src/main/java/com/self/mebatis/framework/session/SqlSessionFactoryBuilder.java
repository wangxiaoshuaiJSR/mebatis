package com.self.mebatis.framework.session;

/**
 *创建SqlSessionFactory，此处使用单例模式
 *{@linkSqlSessionFactoryBuilder}生命周期是只要sqlSessionFactory创建了就没有用了，生命周期就结束
 * Created by wangxiaoshuai on 2019/5/15.
 */
public enum  SqlSessionFactoryBuilder {

    INSTANCE;

    private SqlSessionFactory sqlSessionFactory=null;
    private SqlSessionFactoryBuilder(){
        try {
          sqlSessionFactory = new SqlSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SqlSessionFactory buildSqlSessionFactory(){
        return sqlSessionFactory;
    }
}
