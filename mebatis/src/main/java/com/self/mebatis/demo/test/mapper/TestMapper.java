package com.self.mebatis.demo.test.mapper;

import com.self.mebatis.framework.annotation.Entity;
import com.self.mebatis.framework.annotation.Mapper;
import com.self.mebatis.framework.annotation.Select;

/**
 * Created by wangxiaoshuai on 2019/5/15.
 */
@Mapper
@Entity(TestPOJO.class)
public interface TestMapper {

    @Select("select * from test")
    TestPOJO selectOne();
}
