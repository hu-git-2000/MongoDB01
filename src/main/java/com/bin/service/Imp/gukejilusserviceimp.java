package com.bin.service.Imp;

import com.bin.bean.Order;
import com.bin.bean.gukejilu;
import com.bin.mapper.gukejiluMapper;
import com.bin.service.gukejilusservice;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class gukejilusserviceimp  implements gukejilusservice {
    @Autowired
    private gukejiluMapper gukejiluMapper;
    //列表
    public PageInfo<gukejilu> findbypagess(Map<String, Object> map, Integer page, Integer limit) {
        PageHelper.startPage(page,limit);//开启分页位置
        List<gukejilu> list = gukejiluMapper.findbypagess(map);
        PageInfo<gukejilu> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

}
