package com.iaven;


import com.iaven.mapper.OrderMapper;
import com.iaven.pojo.table.Order;
import com.iaven.service.MailService;
import com.iaven.service.OrderService;
import com.iaven.service.UserService;
import org.mockito.internal.matchers.Or;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Unit test for simple App.
 */
@SpringBootTest
@MapperScan("com.iaven.mapper")
public class AppTest 
{

    @Autowired
    MailService mailService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void contextLoads()
    {
        List<Order> orders = orderService.list_orders();
        System.out.println(orders.size());
        System.out.println(userService.getUserById(1).toString());
    }
}
