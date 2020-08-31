package com.bin.service.Imp;

import com.bin.bean.Master;
import com.bin.bean.MasterAddress;
import com.bin.bean.MasterAddressExample;
import com.bin.mapper.MasterAddressMapper;
import com.bin.mapper.MasterMapper;
import com.bin.service.Masterservice;
import com.bin.util.LngAndLatUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class MasterserviceImp implements Masterservice {

    @Autowired
    private MasterMapper MasterMapper;

    @Autowired
    private MasterAddressMapper masterAddressMapper;

    @Override
    public PageInfo<Master> findMasterpage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
       List<Master> list=MasterMapper.findMastersPage();
        for (Master i:list){
            try {
                String lngAndLat = LngAndLatUtil.getLngAndLat(i.getMasterAddress().getLng(), i.getMasterAddress().getLat());
                i.getMasterAddress().setAddress(lngAndLat);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return new PageInfo<Master>(list);
    }

    @Override
    public int updetamasteraddress(Integer mid) {
        MasterAddress ma=new MasterAddress();
        ma.setStatus("0");//在忙
        ma.setMid(mid);

        MasterAddressExample example=new MasterAddressExample();
        MasterAddressExample.Criteria criteria = example.createCriteria();
        criteria.andMidEqualTo(mid);
        int i = masterAddressMapper.updateByExampleSelective(ma, example);
        return i;
    }

    @Override
    public int relieveMaster(Integer mid) {
        MasterAddress masterAddress = new MasterAddress();
        masterAddress.setMid(mid);
        masterAddress.setStatus("1");//空闲
        MasterAddressExample masterAddressExample = new MasterAddressExample();
        MasterAddressExample.Criteria criteria = masterAddressExample.createCriteria();
        criteria.andIdEqualTo(mid);
        int i = masterAddressMapper.updateByExampleSelective(masterAddress, masterAddressExample);
        return i;

    }

    @Override
    public List listMasterAddress() {
       List<MasterAddress> list=masterAddressMapper.listmasterAddress();
    return list;
    }
}
