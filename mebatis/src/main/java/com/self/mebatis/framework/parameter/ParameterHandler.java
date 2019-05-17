package com.self.mebatis.framework.parameter;

import java.sql.PreparedStatement;

/**
 * 参数处理器
 * Created by wangxiaoshuai on 2019/5/16.
 */
public class ParameterHandler {
    private PreparedStatement psmt;

    public ParameterHandler(PreparedStatement psmt) {
        this.psmt = psmt;
    }

    /**
     * 从方法中获取参数，遍历设置SQL中的？占位符
     * @param parameters
     */
    public void setParameters(Object[] parameters){
        try{
           for(int i=0;i<parameters.length;i++){
               int k = i+1;
               if(parameters[i] instanceof Integer){
                   psmt.setInt(k,(Integer) parameters[i]);
               }else if(parameters[i] instanceof Long){
                   psmt.setLong(k, (Long) parameters[i]);
               }else if(parameters[i] instanceof String){
                   psmt.setString(k, String.valueOf(parameters[i]));
               }else if(parameters[i] instanceof Boolean){
                   psmt.setBoolean(k, (Boolean) parameters[i]);
               }else{
                   psmt.setString(k,String.valueOf(parameters[i]));
               }
           }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
