package com.self.mebatis.framework;



import com.self.mebatis.demo.test.mapper.TestMapper;
import com.self.mebatis.demo.test.mapper.TestPOJO;
import com.self.mebatis.framework.session.DefaultSqlSession;
import com.self.mebatis.framework.session.SqlSessionFactory;
import com.self.mebatis.framework.session.SqlSessionFactoryBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaoshuai on 2019/5/15.
 */
public class Test {
    private static List<String> classPaths = new ArrayList<>();
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.INSTANCE.buildSqlSessionFactory();
        DefaultSqlSession sqlSession = sqlSessionFactory.openSqlSession();
        TestMapper mapper = sqlSession.getMapper(TestMapper.class);
        TestPOJO test = mapper.selectOne();

    }

}
