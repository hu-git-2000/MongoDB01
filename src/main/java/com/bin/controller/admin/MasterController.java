package com.bin.controller.admin;

import com.bin.bean.Master;
import com.bin.service.Masterservice;
import com.bin.util.JsonData;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/master")
public class MasterController {
    @Autowired
    private Masterservice Masterservice;

@RequestMapping("/selectList")
@ResponseBody
    public  Object selectList(@RequestParam(value = "page",defaultValue = "1")Integer page,
                              @RequestParam(value = "limit",defaultValue = "1")Integer limit){

    PageInfo<Master> ma=Masterservice.findMasterpage(page,limit);
        return JsonData.buildSuc(ma);
    }
    @RequestMapping("/tolist")
    public String tolist(){
        return "/static/html/master/table.html";
    }
}
