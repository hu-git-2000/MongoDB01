package com.bin.service;

import com.bin.bean.Customer;
import com.github.pagehelper.PageInfo;

import java.util.Date;
import java.util.Map;

public interface Customerservice {
    int saveCustomer(String phone, Date date, String name, String car);
//前台添加
     int saveCustomers(String phone);

//用户列表
    PageInfo<Customer> customerlist(Map<String, Object> map, Integer page, Integer limit);

    int cupdate(Customer customer, Integer id);
}
