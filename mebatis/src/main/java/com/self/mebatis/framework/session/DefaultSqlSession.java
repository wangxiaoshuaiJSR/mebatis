package com.self.mebatis.framework.session;


import com.self.mebatis.framework.executor.Executor;

/**
 * Created by wangxiaoshuai on 2019/5/15.
 */
public class DefaultSqlSession {
    private Configuration configuration;
    private Executor executor;
    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        executor = configuration.newExecutor();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public <T>T getMapper(Class<T> mapper){
        return configuration.getMapper(mapper,this);
    }

    public <T>T selectOne(String key, Object[] args, Class<?> object) {
        String statementSql = configuration.getMappedStatement(key);
        return executor.query(statementSql,args,object);
    }
}
