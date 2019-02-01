package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.manager.dao.CertMapper;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Elvis Chen
 * @Date 2019/2/1 20:50
 * @Version 1.0
 **/
@Service
public class CertServiceImpl implements CertService {

    @Autowired
    private CertMapper certMapper;

    @Override
    public Cert queryById(Integer id) {
        return certMapper.queryById(id);
    }

    @Override
    public int deleteCerts(Data ds) {
        return certMapper.deleteCerts(ds);
    }

    @Override
    public int deleteCert(Integer id) {
        return certMapper.deleteCert(id);
    }

    @Override
    public int updateCert(Cert cert) {
        return certMapper.updateCert(cert);
    }

    @Override
    public void insertCert(Cert cert) {
        certMapper.insertCert(cert);
    }

    @Override
    public Page<Cert> pageQuery(Map<String, Object> paramMap) {
        Page<Cert> certPage = new Page<Cert>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
        paramMap.put("startIndex", certPage.getStartIndex());
        List<Cert> certs = certMapper.pageQuery(paramMap);
        //获取数据的总条数
        int count = certMapper.queryCount(paramMap);
        certPage.setData(certs);
        certPage.setTotalsize(count);
        return certPage;
    }
}
