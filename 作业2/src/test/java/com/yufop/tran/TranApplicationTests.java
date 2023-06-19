package com.yufop.tran;

import com.yufop.tran.mapper.UserMapper;
import com.yufop.tran.pojo.table.Order;
import com.yufop.tran.pojo.table.User;
import com.yufop.tran.pojo.table.UserExample;
import com.yufop.tran.service.MailService;
import com.yufop.tran.service.OrderService;
import com.yufop.tran.service.UserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@MapperScan("com.yufop.tran.mapper")
class TranApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
//        List<Order> orders = orderService.list_orders(1);
        User  user = userService.loginUser("123","sdf");
        System.out.println(user);

    }

}
