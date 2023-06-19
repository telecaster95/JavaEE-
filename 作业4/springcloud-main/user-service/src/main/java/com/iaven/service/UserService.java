package com.iaven.service;


import com.iaven.pojo.table.User;

import java.util.List;

/**
 * <Description> UserService
 *  与用户有关的业务均在此接口中声明，比如登录，注册等等
 */


public interface UserService {

    int modifyInfo(int ID, User user);

    User registerUser(User user);


    User loginUser(String username, String password);

    String retrievePassword(String username); //根据用户名查询密码并返回，调用此方法的前提是验证码输入正确

    /*通用部分*/
    List<User> listUser(); //获取数据库中的所有用户
    List<User> listUserSelective(User user, int pn); //xzx
    User getUserById(Integer id); //根据id获取用户 fbf
    User getUserByUserName(String username); //xzx
    boolean ifExistUser(String username); //wj
    boolean ifExistUser(Integer id); //wj
    int deleteUser(Integer userID);

}