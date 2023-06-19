package com.iaven.service.impl;



import com.iaven.mapper.UserMapper;
import com.iaven.pojo.table.User;
import com.iaven.pojo.table.UserExample;
import com.iaven.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;




@Service
@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public int modifyInfo(int ID, User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(ID);
        if(userMapper.updateByExampleSelective(user,userExample)>0){
            return 1;
        }
        else{
            return 0;
        }

    }




        @Override
        public User registerUser(User user) {
            // 判断是否已存在该用户
            UserExample userExample = new UserExample();
            userExample.createCriteria().andEmailEqualTo(user.getEmail());
            List<User> userList = userMapper.selectByExample(userExample);
            if (!userList.isEmpty())
            {
                return null;
            }

            // 否则注册为新的用户
            userMapper.insertSelective(user);
            return user;

        }

        @Override
        public User loginUser(String email, String password) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andPasswordEqualTo(password).andEmailEqualTo(email);
            List<User> users =userMapper.selectByExample(userExample);
            if(users!=null){
                if(users.size()==1) {return users.get(0);}
                else {return null;}
            }
            else {return null;}
        }

        @Override
        public String retrievePassword(String username) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUsernameEqualTo(username);
            List<User> users = userMapper.selectByExample(userExample);
            if (users!=null){
                if (users.size()!=0) {
                    return users.get(0).getPassword();
                }
                else {
                    return null;
                }
            }
            return null;
        }



        @Override
        public List<User> listUser() {
            return userMapper.selectByExample(null);
        }

        @Override
        public List<User> listUserSelective(User user, int pn) {
            return null;
        }

        @Override
        public User getUserById(Integer id) {
            return userMapper.selectByPrimaryKey(id);
        }

        @Override
        public User getUserByUserName(String username) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUsernameEqualTo(username);
            List<User> users = userMapper.selectByExample(userExample);
            if (users.size()!=0){
                return users.get(0);
            }
            return null;
        }

        @Override
        public boolean ifExistUser(String username)
        {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andUsernameEqualTo(username);
            List<User> users = userMapper.selectByExample(userExample);
            if (!users.isEmpty())
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        @Override
        public boolean ifExistUser(Integer id)
        {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(id);
            List<User> users = userMapper.selectByExample(userExample);
            if (!users.isEmpty())
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        @Override
        public int deleteUser(Integer userID){
            return userMapper.deleteByPrimaryKey(userID);
        }



}
