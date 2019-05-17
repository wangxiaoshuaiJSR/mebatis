package com.self.mebatis.framework.executor;

/**
 * Created by wangxiaoshuai on 2019/5/16.
 */
public class SimpleExecutor implements Executor {
    @Override
    public <T> T query(String statementSql, Object[] args, Class<?> object) {
        Statementhandler statementhandler = new Statementhandler();
        return statementhandler.query(statementSql,args,object);
    }
}
