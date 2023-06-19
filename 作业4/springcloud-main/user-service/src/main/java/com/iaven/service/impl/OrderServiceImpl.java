package com.iaven.service.impl;

import com.iaven.mapper.OrderMapper;
import com.iaven.pojo.table.Order;
import com.iaven.pojo.table.OrderExample;
import com.iaven.service.OrderService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.iaven.utils.Global.*;


@Service
@Repository
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;


    @Override
    public List<Order> list_orders(int id) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUseridEqualTo(id).andStateNotEqualTo(DELE).andStateNotEqualTo(FINISH);
        return orderMapper.selectByExample(orderExample);
    }

    @Override
    public List<Order> listbyrename(int id, String name) {
        OrderExample orderExample = new OrderExample();
        if(id>0){
            orderExample.createCriteria().andUseridEqualTo(id).andRecnameEqualTo(name);
        }
        else if(id==0){
            orderExample.createCriteria().andRecnameEqualTo(name);
        }

        return orderMapper.selectByExample(orderExample);

    }

    @Override
    public List<Order> listbyaddress(int id, String add) {
        OrderExample orderExample = new OrderExample();
        if(id>0){
            orderExample.createCriteria().andUseridEqualTo(id).andRecaddressEqualTo(add);
        }
        else if(id==0){
            orderExample.createCriteria().andRecaddressEqualTo(add);
        }

        return orderMapper.selectByExample(orderExample);
    }

    @Override
    public int submit_order(Order order) {
        return orderMapper.insertSelective(order);
    }

    @Override
    public int revoke_order(int order_id) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andIdEqualTo(order_id);
        return orderMapper.deleteByExample(orderExample);
    }

    @Override
    public int update_state(int orderId, String state) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andIdEqualTo(orderId);
        Order order = new Order();
        order.setState(state);
        if(orderMapper.updateByExampleSelective(order,orderExample)>0){
            return 1;
        }
        return 0;
    }

    @Override
    public List<Order> search_bill(int userid) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUseridEqualTo(userid).andStateEqualTo(FINISH);
        return orderMapper.selectByExample(orderExample);
    }

    @Override
    public int delete_bill(int orderid) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andIdEqualTo(orderid);
        Order order = new Order();
        order.setState(DELE);
        return orderMapper.updateByExampleSelective(order,orderExample);
    }
}
