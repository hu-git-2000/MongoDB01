package com.bin.controller;

import com.bin.util.MapUtils;
import com.bin.util.RedisUtil;
import com.bin.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 作用，展示手机前端页面 提交订单，实现登录
 */
@Controller
@RequestMapping("/cphone")
public class CustomerphoneController {

    @Autowired
    private RedisUtil RedisUtil;


    @RequestMapping("/index")
    public String toIndex(){
    return "/static/html/customerPhone/index.html";
}


/**
 * 返回两个坐标间距的金额
 *接受两个经纬度
 */
@RequestMapping("/getMoney")
    @ResponseBody
    public String getMoney(String lng, String lat,String oldLng,String oldLat){
    int  result = (int) MapUtils.algorithm(Double.valueOf(lng), Double.valueOf(lat), Double.valueOf(oldLng), Double.valueOf(oldLat))/100;

    return result+"";
}

@RequestMapping("/isLogin")
@ResponseBody
public String isLogin(HttpSession seession){
//session
    String phone = (String) seession.getAttribute("phone");
    if (StringUtils.isEmpty(phone)){
        return "error";
        }else{
        return "success";
    }

    //token

}

    @RequestMapping("/to_login")
    public String to_login(){
        return "/static/html/customerPhone/html/login.html";
    }

    @RequestMapping("/login")
    @ResponseBody
public  Object login(String phone,String code,HttpSession session){
        System.out.println(phone+"----------"+ code+"------");
        Map<String,Object> map=new HashMap<String, Object>();
        if (StringUtils.isEmpty(phone)){
            map.put("type","error");
            map.put("msg","电话号码不能为空");
            return map;
        }
        if (StringUtils.isEmpty(code)){
            map.put("type","error");
            map.put("msg","验证码不能为空");
            return map;
        }
        //取出redis数据库 fourRandom
        String o = (String) RedisUtil.get(phone);
        if (StringUtils.isEmpty(o)){
            map.put("type","error");
            map.put("msg","验证码失效");
            return map;
        }else {
            if (!code.equals(o)) {
                map.put("type", "error");
                map.put("msg", "验证码错误");
                return map;
            }
        }

        session.setAttribute("phone",phone);
        map.put("type", "success");
        map.put("msg", "登录成功");
        return map;
}
@RequestMapping("/sendSms")
@ResponseBody
    public  String sendSms(String phone){
    //拿到随机验证码
    String fourRandom = SmsUtil.getFourRandom();
    String s = SmsUtil.sendSms(phone, fourRandom);
    if (s.contains("OK")){
    //将手机号做位key值  验证码作为value 存入redis 数据库  判断失效时间
    RedisUtil.setEx(phone,fourRandom,5, TimeUnit.MINUTES);
    }else  {
        return "验证码接受失败";
    }
    return fourRandom;
    }

}
