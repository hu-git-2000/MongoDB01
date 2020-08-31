package com.bin.mapper;

import com.bin.bean.gukejilu;
import com.bin.bean.gukejiluExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface gukejiluMapper {
    int countByExample(gukejiluExample example);

    int deleteByExample(gukejiluExample example);

    int deleteByPrimaryKey(Integer gid);

    int insert(gukejilu record);

    int insertSelective(gukejilu record);

    List<gukejilu> selectByExample(gukejiluExample example);

    gukejilu selectByPrimaryKey(Integer gid);

    int updateByExampleSelective(@Param("record") gukejilu record, @Param("example") gukejiluExample example);

    int updateByExample(@Param("record") gukejilu record, @Param("example") gukejiluExample example);

    int updateByPrimaryKeySelective(gukejilu record);

    int updateByPrimaryKey(gukejilu record);


    List<gukejilu> findbypagess(Map<String, Object> map);
}