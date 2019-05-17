package com.self.mebatis.framework.executor;

/**
 * Created by wangxiaoshuai on 2019/5/16.
 */
public interface Executor {
    public <T> T query(String statementSql, Object[] args, Class<?> object);
}
