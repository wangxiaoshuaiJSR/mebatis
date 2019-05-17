package com.self.mebatis.framework.executor;

import com.self.mebatis.framework.parameter.ParameterHandler;
import com.self.mebatis.framework.session.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 封装JDBC，用于操作数据库
 * Created by wangxiaoshuai on 2019/5/16.
 */
public class Statementhandler {
    ResultSetHandler resultSetHandler=new ResultSetHandler();
    public <T>T query(String statement,Object[] parameter,Class pojo){
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        Object result = null;
        try{
            conn = getConnection();
            preparedStatement = conn.prepareStatement(statement);
            ParameterHandler parameterHandler = new ParameterHandler(preparedStatement);
            parameterHandler.setParameters(parameter);
            preparedStatement.execute();
            result = resultSetHandler.handle(preparedStatement.getResultSet(),pojo);
            return (T) result;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                conn = null;
            }
        }
        return null;
    }

    /**
     * 获取连接
     * @return
     */
    public Connection getConnection() {
        String driver = Configuration.properties.getString("jdbc.driver");
        String url = Configuration.properties.getString("jdbc.url");
        String username = Configuration.properties.getString("jdbc.username");
        String password = Configuration.properties.getString("jdbc.password");
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
