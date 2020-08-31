package com.bin.service.Imp;

import cn.hutool.core.date.DateTime;
import com.bin.bean.Order;
import com.bin.bean.OrderExample;
import com.bin.mapper.OrderMapper;
import com.bin.service.Orderservice;
import com.bin.util.JsonData;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class OrderserviceImp implements Orderservice {
    @Autowired
private OrderMapper orderMapper;

    @Override
    public PageInfo<Order> findbypages(Map<String, Object> map, Integer page, Integer limit) {

        PageHelper.startPage(page,limit);  //开启分页位置
     List<Order>  list=orderMapper.findbypages(map);
           PageInfo<Order> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public List <Order>  findByPhone(String phone) {
        OrderExample example=new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<Order> list = orderMapper.selectByExample(example);
        return list;
    }

    @Override
    public int saveorder(Order order) {
        order.setStatus("0");
        int i = orderMapper.insertSelective(order);
        return i;
    }

    @Override
    public int saveorders(Order order) {
        order.setStatus("0");
        int i = orderMapper.insertSelective(order);
        return i;
    }

    @Override
    public int updateorderbyid(Integer mid,Integer id) {
        Order order=new Order();
        order.setId(id);
        order.setStatus("1");
        order.setMid(mid);
        int i = orderMapper.updateByPrimaryKeySelective(order);
        return i;
    }

    //根据id擦寻状态
    @Override
    public Order findBylist(Integer id) {
        Order order = orderMapper.selectByPrimaryKey(id);
        return order;
    }

    @Override
    public int deleteorderbyid(Integer id) {
        int i = orderMapper.deleteByPrimaryKey(id);
        return i;
    }

    @Override
    public int relieveMaster(Integer id) {
        Order order=new Order();
        order.setId(id);
        order.setStatus("-1");
        int i = orderMapper.updateByPrimaryKeySelective(order);
        return i;
    }

    @Override
    public int upd(Order order, Integer id) {
        Order o = new Order();
        o.setId(order.getId());
        o.setAddress(order.getAddress());
        o.setContents(order.getContents());
        o.setCost(order.getCost());
        o.setLng(order.getLng());
        o.setLat(order.getLat());
        o.setUpdtetime(new DateTime());
        int i=orderMapper.updateByPrimaryKeySelective(o);
        return i;

    }

    @Override
    public List<Order> list() {
       List <Order> list= orderMapper.selectlist();
        return list;
    }


    public int updatestatus(Integer id,String status) {
        Order o = new Order();
        o.setId(id);
        o.setStatus(status);
        int i = orderMapper.updateByPrimaryKeySelective(o);
        return i;
    }




}
