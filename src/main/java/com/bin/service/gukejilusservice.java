package com.bin.service;


import com.bin.bean.gukejilu;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface gukejilusservice {
   PageInfo<gukejilu> findbypagess(Map<String, Object> map, Integer page, Integer limit);
}
