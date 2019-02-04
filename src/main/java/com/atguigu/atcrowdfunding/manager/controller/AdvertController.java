package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Advert;
import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.manager.service.AdvertService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @Author Elvis Chen
 * @Date 2019/2/2 0:51
 * @Version 1.0
 **/
@Controller
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index() {
        return "advert/index";
    }

    @RequestMapping("/add")
    public String add(){
        return "advert/add";
    }

    @RequestMapping("/edit")
    public String edit(Integer id, Model model) {
        Advert advert = advertService.queryById(id);
        model.addAttribute("advert", advert);
        return "advert/edit";
    }

    @RequestMapping("/batchDelete")
    @ResponseBody
    public Object batchDelete(Data ds) {
        AjaxResult result = new AjaxResult();

        try {
            int count = advertService.deleteAdverts(ds);
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
            int count = advertService.deleteAdvert(id);
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
    public Object update(Advert advert) {

        AjaxResult result = new AjaxResult();

        try {
            int count = advertService.updateAdvert(advert);
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

    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(HttpServletRequest request, Advert advert, HttpSession session) {
        AjaxResult result = new AjaxResult();

        try {
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;

            MultipartFile mfile = mreq.getFile("advpic");

            String name = mfile.getOriginalFilename();
            String extname = name.substring(name.lastIndexOf("."));

            String iconpath = UUID.randomUUID().toString() + extname;

            ServletContext servletContext = session.getServletContext();
            String realpath = servletContext.getRealPath("/pics");

            String path = realpath + "\\adv\\" + iconpath;

            mfile.transferTo(new File(path));

            User user = (User) session.getAttribute(Const.LOGIN_USER);
            advert.setUserid(user.getId());
            advert.setStatus("1");
            advert.setIconpath(iconpath);

            int count = advertService.insertAdvert(advert);
            result.setSuccess(count == 1);
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
            Map<String, Object> advertMap =new HashMap<String, Object>();
            advertMap.put("pageno", pageno);
            advertMap.put("pagesize", pagesize);
            if(StringUtil.isNotEmpty(pagetext)){
                pagetext = pagetext.replaceAll("%", "\\\\%");
            }
            advertMap.put("pagetext", pagetext);

            //分页查询
            Page<Advert> page = advertService.pageQuery(advertMap);
            result.setPage(page);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }


















}
