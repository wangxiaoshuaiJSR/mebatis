package com.self.mebatis.framework.executor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * 结果集处理器
 * Created by wangxiaoshuai on 2019/5/16.
 */
public class ResultSetHandler {

    public <T>T handle(ResultSet resultSet,Class clazz){
        Object pojo = null;
        try {
            pojo = clazz.newInstance();
            if(resultSet.next()){
                for(Field field:clazz.getDeclaredFields()){
                    setValue(pojo,field,resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) pojo;
    }

    private void setValue(Object object,Field field,ResultSet resultSet){
        try {
            Method method = object.getClass().getMethod("set"+toUpperCaseFirstChar(field.getName()),field.getType());
            method.invoke(object,getResult(resultSet,field));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据字段名称和类型从resultset中得字段的值
     * @param resultSet
     * @param field
     * @return
     * @throws SQLException
     */
    private Object getResult(ResultSet resultSet,Field field) throws SQLException {
        Class<?> type = field.getType();
        String fieldName = humpToUnderline(field.getName());
        if(Integer.class==type){
            return resultSet.getInt(fieldName);
        }else if(String.class==type){
            return resultSet.getString(fieldName);
        }else if(Long.class==type){
            return resultSet.getLong(fieldName);
        }else if(Boolean.class==type){
            return resultSet.getBoolean(fieldName);
        }else if(Double.class==type){
            return resultSet.getDouble(fieldName);
        }else{
            return resultSet.getString(fieldName);
        }
    }
    /**
     * 单词首字母大写
     * @param str
     * @return
     */
    private String toUpperCaseFirstChar(String str){
        String firstChar = str.substring(0,1);
        String tail = str.substring(1);
        return firstChar.toUpperCase()+tail;
    }

    /**
     * java驼峰命名转化为下划线
     * @param para
     * @return
     */
    public static String humpToUnderline(String para){
        StringBuilder stringBuilder = new StringBuilder(para);
        if(!para.contains("_")){
            int temp =0;
            for(int i=0;i<para.length();i++){
                if(Character.isUpperCase(para.charAt(i))){
                    stringBuilder.insert(i+temp,"_");
                    temp++;
                }
            }
        }
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * 结果映射高端玩法
     */
    private <T>T populate(ResultSet resultSet,T obj){
        try{
            ResultSetMetaData resultSetMetaData=resultSet.getMetaData();
            int count = resultSetMetaData.getColumnCount();//取得所有列的个数
            Field[] fields = obj.getClass().getDeclaredFields();
            for(int i=0;i<fields.length;i++){
                Field field = fields[i];
                //rs的游标从1开始
                for(int j=1;j<=count;j++){
                    Object value = resultSet.getObject(j);
                    String colname = resultSetMetaData.getColumnName(j);
                    if(!field.getName().equalsIgnoreCase(colname)){
                        continue;
                    }
                    //如果列名中有和字段名一样的，则设置值
                    //可以用apache的BeanUtils加反射实现
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return obj;
    }

}
