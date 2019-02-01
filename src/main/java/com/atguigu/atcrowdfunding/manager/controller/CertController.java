package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Cert;
import com.atguigu.atcrowdfunding.manager.service.CertService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Elvis Chen
 * @Date 2019/2/1 20:29
 * @Version 1.0
 **/
@Controller
@RequestMapping("/cert")
public class CertController {

    @Autowired
    private CertService certService;

    @RequestMapping("/index")
    public String index(){
        return "cert/index";
    }

    @RequestMapping("/add")
    public String add(){
        return "cert/add";
    }

    @RequestMapping("/edit")
    public String edit(Integer id, Model model){
    //根据主键查询资质信息
        Cert cert =certService.queryById(id);
        model.addAttribute("cert", cert);
        return "cert/edit";
    }

    @RequestMapping("/deletes")
    @ResponseBody
    public Object deletes(Data ds) {
        AjaxResult result = new AjaxResult();

        try {
            int count = certService.deleteCerts(ds);
            if (count == ds.getDatas().size()) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Integer id) {
        AjaxResult result = new AjaxResult();

        try {
            int count = certService.deleteCert(id);
            if (count == 1) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(Cert cert) {

        AjaxResult result = new AjaxResult();

        try {
            int count = certService.updateCert(cert);
            if (count == 1) {
                result.setSuccess(true);
            } else {
                result.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Object insert(Cert cert) {
        AjaxResult result = new AjaxResult();

        try {
            certService.insertCert(cert);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/pageQuery")
    @ResponseBody
    public Object pageQuery(String pagetext, Integer pageno, Integer pagesize) {

        AjaxResult result = new AjaxResult();

        try {
            //查询资质数据
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("pageno", pageno);
            paramMap.put("pagesize", pagesize);
            if (StringUtil.isNotEmpty(pagetext)) {
            }
            paramMap.put("pagetext", pagetext);

            //分页查询数据
            Page<Cert> page = certService.pageQuery(paramMap);
            result.setPage(page);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }


























}
