package com.iaven.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iaven.pojo.table.Order;
import com.iaven.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrderService orderService;
    /**
     * 列出用户的所有订单
     * @param pageNum
     * @param pageSize
     * @param userid
     * @return
     */
    @GetMapping("/list_order")
    @ResponseBody
    public PageInfo<Order> get_allrecords(@RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize")Integer pageSize, int userid){
        PageHelper.startPage(pageNum,pageSize);

        List<Order> orders = orderService.list_orders(userid);
        PageInfo<Order> pageInfo = new PageInfo<Order>(orders);
        return pageInfo;
    }




    /**
     * 根据收件人姓名查询订单
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/search_orderbyname")
    @ResponseBody
    public PageInfo<Order> get_recordsbyname(@RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize")Integer pageSize,int userid,String name){
        PageHelper.startPage(pageNum,pageSize);

        List<Order> orders = orderService.listbyrename(userid,name);
        PageInfo<Order> pageInfo = new PageInfo<Order>(orders);
        return pageInfo;
    }


    /**
     * 根据收获地址查询
     * @param pageNum
     * @param pageSize
     * @param userid
     * @param address
     * @return
     */
    @GetMapping("/search_orderbyaddress")
    @ResponseBody
    public PageInfo<Order> get_recordsbyaddress(@RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize")Integer pageSize,int userid,String address){
        PageHelper.startPage(pageNum,pageSize);

        List<Order> orders = orderService.listbyaddress(userid,address);
        PageInfo<Order> pageInfo = new PageInfo<Order>(orders);
        return pageInfo;
    }


    /**
     * 计算并列出不同的物流公司对该订单的预计送达时间和花费
     * @param pageNum
     * @param pageSize
     * @param order
     * @return
     */
    @GetMapping("/list_orderdetails")
    @ResponseBody
    public PageInfo<Object> get_allrecords(@RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize")Integer pageSize, Order order){
        PageHelper.startPage(pageNum,pageSize);

        /**TODO
         *
         */
        PageInfo<Object> pageInfo = new PageInfo<>();
        return pageInfo;
    }

    /**
     * 选择物流公司提交订单
     * @param order
     * @return
     */
    @PostMapping("/submit_order")
    @ResponseBody
    public int order(Order order){


        return orderService.submit_order(order);
    }


    /**
     * 撤销订单（退款）
     * @param orderid
     * @return
     */
    @PostMapping("/revoke_order")
    @ResponseBody
    public int revoke(int orderid){

        return orderService.delete_bill(orderid);
    }

    /**
     * 查看用户账单
     * @param userid
     * @return
     */
    @GetMapping("/search_bill")
    @ResponseBody
    public PageInfo<Order> search_bill(@RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize")Integer pageSize,int userid){

        PageHelper.startPage(pageNum,pageSize);

        List<Order> orders = orderService.search_bill(userid);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }


    /**
     * 删除账单
     * @param orderid
     * @return
     */
    @PostMapping("/delete_bill")
    @ResponseBody
    public int delete_bill(int orderid){
        return orderService.delete_bill(orderid);
    }





}
