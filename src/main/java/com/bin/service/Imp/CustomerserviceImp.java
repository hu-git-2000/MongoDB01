package com.bin.service.Imp;

import com.bin.bean.Customer;
import com.bin.bean.CustomerExample;
import com.bin.bean.Order;
import com.bin.bean.OrderExample;
import com.bin.mapper.CustomerMapper;
import com.bin.service.Customerservice;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerserviceImp implements Customerservice {
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public int saveCustomer(String phone, Date date, String name, String car) {
        Customer customer=new Customer();
        customer.setPhone(phone);
        customer.setCreatetime(date);
        customer.setName(name);
        customer.setCar(car);
        
        int i = customerMapper.insertSelective(customer);
        return i;
    }

    @Override
    public int saveCustomers(String phone) {
        Customer customer=new Customer();
        customer.setPhone(phone);
        customer.setCreatetime(new Date());
        int i = customerMapper.insertSelective(customer);
        return i;
    }

    @Override
    public PageInfo<Customer> customerlist(Map<String, Object> map, Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
       List<Customer> list=customerMapper.selectcustomer(map);
        System.out.println(list.size());
        PageInfo<Customer> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int cupdate(Customer customer, Integer id) {
        Customer c=new Customer();
        c.setId(id);
        c.setPhone(customer.getPhone());
        c.setName(customer.getName());
        c.setIdcard(customer.getIdcard());
        c.setCar(customer.getCar());
        int i = customerMapper.updateByPrimaryKeySelective(customer);
        return i;
    }


}
