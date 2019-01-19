package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.exception.LoginFailException;
import com.atguigu.atcrowdfunding.manager.dao.UserMapper;
import com.atguigu.atcrowdfunding.manager.service.UserService;
import com.atguigu.atcrowdfunding.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Elvis Chen
 * @Date 2019/1/12 1:53
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryUserlogin(Map<String, Object> paramMap) {
        User user = userMapper.queryUserlogin(paramMap);
        if(user == null){
            throw new LoginFailException("用户账号或密码不正确!");
        }
        return user;
    }

     @Override
     public Page queryPage(Map<String, Object> paramMap) {
        Page page = new Page((Integer)paramMap.get("pageno"), (Integer)paramMap.get("pagesize"));
        Integer startIndex = page.getStartIndex();
        paramMap.put("startIndex", startIndex);
        List<User> datas = userMapper.queryList(paramMap);
        page.setDatas(datas);
        Integer totalsize = userMapper.queryCount(paramMap);
        page.setTotalsize(totalsize);
        return page;
    }

    @Override
    public int saveUser(User user) {
        return userMapper.insert(user);
    }
}
