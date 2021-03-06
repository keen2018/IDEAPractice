package com.hisen.mybatisStudy.no4.mapper;

import com.hisen.mybatisStudy.po.User;
import com.hisen.mybatisStudy.po.UserCustom;
import com.hisen.mybatisStudy.po.UserQueryVo;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hisen on 17-3-25.
 */
public class UserMapperTest {

  private SqlSessionFactory sqlSessionFactory;

  //注解Before是在执行本类所有测试方法之前先调用这个方法
  @Before
  public void setup() throws Exception {
    //创建SqlSessionFactory
    String resource = "SqlMapConfig.xml";
    //将配置文件加载成流
    InputStream inputStream = Resources.getResourceAsStream(resource);
    //创建会话工厂，传入mybatis配置文件的信息
    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
  }

  @Test
  public void testFindUserById() throws Exception {

    SqlSession sqlSession = sqlSessionFactory.openSession();

    //创建UserMapper代理对象
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

    //调用userMapper的方法
    User user = userMapper.findUserById(1);

    System.out.println(user.getUsername());
  }

  //用户信息的综合 查询
  @Test
  public void testFindUserList() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    //创建UserMapper对象，mybatis自动生成mapper代理对象
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    //创建包装对象，设置查询条件
    UserQueryVo userQueryVo = new UserQueryVo();
    UserCustom userCustom = new UserCustom();
    //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
    userCustom.setSex("1");
    userCustom.setUsername("张三");
    userQueryVo.setUserCustom(userCustom);
    //调用userMapper的方法(传进去一个对象，在sql语句里面获取字段)
    List<UserCustom> list = userMapper.findUserList(userQueryVo);
    System.out.println(list);
  }

  //用户信息综合查询总数
  @Test
  public void testFindUserCount() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    //创建UserMapper对象，mybatis自动生成mapper代理对象
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    //创建包装对象，设置查询条件
    UserQueryVo userQueryVo = new UserQueryVo();
    UserCustom userCustom = new UserCustom();
    //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
    userCustom.setSex("1");
    userCustom.setUsername("小");
    userQueryVo.setUserCustom(userCustom);
    //调用userMapper的方法
    int count = userMapper.findUserCount(userQueryVo);
    System.out.println(count);

  }

  @Test
  public void testFindUserByIdResultMap() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    //创建UserMapper对象，mybatis自动生成mapper代理对象
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    //调用userMapper的方法
    User user = userMapper.findUserByIdResultMap(1);
    System.out.println(user);
  }

  //用户信息的综合 查询 - 带判断条件
  @Test
  public void testFindUserList_if() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    //创建UserMapper对象，mybatis自动生成mapper代理对象
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    //创建包装对象，设置查询条件
    UserQueryVo userQueryVo = new UserQueryVo();
    UserCustom userCustom = new UserCustom();
    //由于这里使用动态sql，如果不设置某个值，条件不会拼接在sql中
    userCustom.setSex("1");
    //userCustom.setUsername("张三");
    //注释了username字段，sql里面就判断了：SELECT * FROM user WHERE user.sex=? AND user.username LIKE '%%'
    userQueryVo.setUserCustom(userCustom);

    //调用userMapper的方法(传进去一个对象，在sql语句里面获取字段)
    //List<UserCustom> list = userMapper.findUserList(userQueryVo);

    List<UserCustom> list = userMapper.findUserList_if(null);
    //SELECT * FROM user 如果传入的对象为空，where条件没了
    System.out.println(list);
  }

  //用户信息的综合 查询 - SQL片段
  @Test
  public void testFindUserList_one() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    //创建UserMapper对象，mybatis自动生成mapper代理对象
    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    //创建包装对象，设置查询条件
    UserQueryVo userQueryVo = new UserQueryVo();
    //传入多个id
    List<Integer> ids = new ArrayList<Integer>();
    ids.add(1);
    ids.add(10);
    ids.add(16);
    userQueryVo.setIds(ids);
    //调用userMapper的方法(传进去一个对象，在sql语句里面获取字段)
    List<UserCustom> list = userMapper.findUserList_one(userQueryVo);
    System.out.println(list);
  }
}
