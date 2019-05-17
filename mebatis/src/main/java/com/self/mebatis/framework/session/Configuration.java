package com.self.mebatis.framework.session;

import com.self.mebatis.framework.annotation.Entity;
import com.self.mebatis.framework.annotation.Mapper;
import com.self.mebatis.framework.annotation.Select;
import com.self.mebatis.framework.binding.MapperRegistry;
import com.self.mebatis.framework.executor.Executor;
import com.self.mebatis.framework.executor.SimpleExecutor;
import com.self.mebatis.framework.plugin.Interceptor;
import com.self.mebatis.framework.plugin.InterceptorChain;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 解析配置文件，解析所有的配置文件
 * Created by wangxiaoshuai on 2019/5/15.
 */
public class Configuration {

    public static final ResourceBundle properties;
    public static final MapperRegistry mapperRegistry = new MapperRegistry(); //储存接口和工厂类关系
    public static final Map<String,String> mappedStatement = new HashMap<>();//维护接口方法名和Sql关系
    private List<String> classes = new ArrayList<>();//加载编译完的class文件
    private List<Class<?>> mapperList = new ArrayList<>();//所有的mapper映射接口装进去
    private InterceptorChain interceptorChain = new InterceptorChain();//插件

    static {
        properties = ResourceBundle.getBundle("mebatis");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Configuration that = (Configuration) o;

        if (classes != null ? !classes.equals(that.classes) : that.classes != null) return false;
        if (mapperList != null ? !mapperList.equals(that.mapperList) : that.mapperList != null) return false;
        return interceptorChain != null ? interceptorChain.equals(that.interceptorChain) : that.interceptorChain == null;
    }

    @Override
    public int hashCode() {
        int result = classes != null ? classes.hashCode() : 0;
        result = 31 * result + (mapperList != null ? mapperList.hashCode() : 0);
        result = 31 * result + (interceptorChain != null ? interceptorChain.hashCode() : 0);
        return result;
    }

    //构造方法中解析配置文件
    public Configuration() {
        //1.解析所有的mapper配置文件
        String mapperPath = properties.getString("mapper.path");
        scanMappers(mapperPath);
        //2.装载mapper映射信息到容器
        for (Class<?> clazz : mapperList) {
            parsingClass(clazz);
        }

        //3.解析插件，可配置多个插件
        String pluginValue = properties.getString("plugin.path");
        String[] plugins = pluginValue.split(",");
        if(plugins!=null){
            for (String plugin : plugins) {
                Interceptor interceptor = null;
                try {
                    interceptor = (Interceptor) Class.forName(plugin).newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                interceptorChain.addInterceptor(interceptor);
            }
        }
    }

    //解析mapper接口上的注解，包括类上的和方法上的
    private void parsingClass(Class<?> mapper){
        //解析mapper文件和实体类
        if(mapper.isAnnotationPresent(Mapper.class)&&mapper.isAnnotationPresent(Entity.class)){
            for (Annotation annotation : mapper.getAnnotations()) {
                if(annotation.annotationType().equals(Entity.class)){
                    mapperRegistry.addMapper(mapper,((Entity)annotation).value());
                }
            }
        }
        //解析方法名和sql的对应关系
        Method[] methods = mapper.getMethods();
        for (Method method : methods) {
            if(method.isAnnotationPresent(Select.class)){
                String methodName = mapper.getName()+"."+method.getName();
                mappedStatement.put(methodName,method.getAnnotation(Select.class).value());
            }
        }
    }
    //扫描配置的mapper文件包
    private void scanPackage(String mapperPath) {
        String classPath =this.getClass().getResource("/").getPath();
        String path =classPath +"/" +mapperPath.replaceAll("\\.","/");
        File files = new File(path);
        for (File file: files.listFiles()) {
            if(file.isDirectory()){
                scanPackage(mapperPath+"."+file.getName());
            }else{
                if(file.getName().endsWith(".class")){
                    classes.add(mapperPath+"."+file.getName().replace(".class",""));
                }
            }
        }
    }

    //加载mapper文件
    private void scanMappers(String mapperPath){
        scanPackage(mapperPath);
        for (String className : classes) {
            try {
                Class<?> clazz = Class.forName(className);
                if(clazz.isInterface()){
                    mapperList.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public <T> T getMapper(Class<T> mapper,DefaultSqlSession sqlSession) {
        return mapperRegistry.getMapper(mapper,sqlSession);
    }

    public boolean hasStatement(String key) {
        return mappedStatement.containsKey(key);
    }

    //根据方法全路径类名得到sql语句
    public String getMappedStatement(String key) {
        return mappedStatement.get(key);
    }

    public Executor newExecutor() {
        Executor executor=null;

        /**
         * 命中缓存的逻辑没有实现，大概逻辑就声明一个Map，把sql和传入的hash值作为key，查询的结果作为value，如果hash值命中，则直接取值
         */
      /*  if(properties.getString("cache.enabled").equals("true")){

        }else {*/
            executor = new SimpleExecutor();
       /* }*/
       if(interceptorChain.hasPlugin()){
           return (Executor) interceptorChain.pluginAll(executor);
       }
        return executor;
    }
}
