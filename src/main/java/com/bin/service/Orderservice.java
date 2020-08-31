package com.bin.service;

import com.bin.bean.Order;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface Orderservice {
     //分页擦寻
     PageInfo<Order> findbypages(Map<String, Object> map, Integer page, Integer limit);
//根据手机号查询订单状态
List <Order> findByPhone(String phone);
     int saveorder(Order order);
//前台添加
     int saveorders(Order order);
//
    int updateorderbyid(Integer mid,Integer id);

    Order findBylist(Integer id);

    int deleteorderbyid(Integer id);

    int relieveMaster(Integer id);

    int upd(Order order, Integer id);

    List<Order> list();

    int updatestatus(Integer id,String status);



}
