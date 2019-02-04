package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Advert;
import com.atguigu.atcrowdfunding.manager.dao.AdvertMapper;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Elvis Chen
 * @Date 2019/2/2 1:08
 * @Version 1.0
 **/
@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertMapper advertMapper;

    @Override
    public Advert queryById(Integer id) {
        return advertMapper.queryById(id);
    }

    @Override
    public int deleteAdverts(Data ds) {
        return advertMapper.deleteAdverts(ds);
    }

    @Override
    public int deleteAdvert(Integer id) {
        return advertMapper.deleteAdvert(id);
    }

    @Override
    public int updateAdvert(Advert advert) {
        return advertMapper.updateAdvert(advert);
    }

    @Override
    public int insertAdvert(Advert advert) {
        return advertMapper.insertAdvert(advert);
    }

    @Override
    public Page<Advert> pageQuery(Map<String, Object> paramMap) {
       Page<Advert> advertPage = new Page<Advert>((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
        paramMap.put("startIndex", advertPage.getStartIndex());
        List<Advert> advertList =advertMapper.pageQuery(paramMap);
        //获取数据的总条数
        int count  =advertMapper.queryCount(paramMap);

        advertPage.setData(advertList);
        advertPage.setTotalsize(count);
        return advertPage;
    }
}
