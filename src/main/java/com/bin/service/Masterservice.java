package com.bin.service;

import com.bin.bean.Master;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface Masterservice {

    PageInfo<Master> findMasterpage(Integer page, Integer limit);

    int updetamasteraddress(Integer mid);

    int relieveMaster(Integer mid);

    List listMasterAddress();
}
