package com.bin.controller.admin;

import com.bin.bean.Customer;
import com.bin.bean.Order;
import com.bin.service.Customerservice;
import com.bin.util.JsonData;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private Customerservice Customerservice;

    @RequestMapping("/to_selectList")
    public String to_selectList(){
        return "/static/html/customer/table.html";
    }

    @RequestMapping("/selectList")
    @ResponseBody
    public  Object Customerlist(String phone, String name,@RequestParam(value = "page",defaultValue = "1")Integer page,
                                                          @RequestParam(value = "limit",defaultValue = "5")Integer limit){
        Map<String,Object> map=new HashMap<>();
            if (!StringUtils.isEmpty(phone)){
                map.put("phone",phone);
            }
        if (!StringUtils.isEmpty(name)){
            map.put("name",name);
        }
        PageInfo<Customer> pageInfo=Customerservice.customerlist(map,page,limit);

        return JsonData.buildSuc(pageInfo);
    }

    @RequestMapping("/upcustomer")
    public String updatecustomer(){
        System.out.println("abc--------");
        return "/static/html/customer/updCustomer.html";
    }

    @RequestMapping("/upcust")
    @ResponseBody
    public Object cupd(Customer customer, Integer id) {
      int i=  Customerservice.cupdate(customer,id);
        System.out.println(i);
        return i;
    }
}
