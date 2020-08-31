package com.bin.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 客服 controller   管理后台
 */
@Controller
@RequestMapping("/waiter")
public class WaiterController {


    @RequestMapping("/index")
    public  String index(){

        return "/static/html/customerService/back.html";
    }
   // Controller
   // DispatcherServlet
    //handlerMapping
}
