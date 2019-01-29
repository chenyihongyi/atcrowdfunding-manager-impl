package com.atguigu.atcrowdfunding.manager.service.impl;

import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.bean.RolePermission;
import com.atguigu.atcrowdfunding.manager.dao.RoleMapper;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Elvis Chen
 * @Date 2019/1/22 3:13
 * @Version 1.0
 **/
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void saveRole(Role user) {
        roleMapper.insert(user);
    }

    @Override
    public int saveRolePermissionRelationship(Integer roleid, Data datas) {
        roleMapper.deleteRolePermissionRelationship(roleid);
        int totalCount = 0;
        List<Integer> ids = datas.getIds();
        for (Integer permissionid : ids) {
            RolePermission rp = new RolePermission();
            rp.setRoleid(roleid);
            rp.setPermissionid(permissionid);
            int count = roleMapper.insertRolePermission(rp);
            totalCount += count;
        }
        return totalCount;
    }

    @Override
    public int batchDeleteRole(Integer[] uid) {
        return roleMapper.batchDelete(uid);
    }

    @Override
    public int deleteRole(Integer uid) {
        return roleMapper.delete(uid);
    }

    @Override
    public int updateRole(Role user) {
        return roleMapper.update(user);
    }

    @Override
    public Role getRole(Integer id) {
        return roleMapper.getRole(id);
    }

    @Override
	public int batchDeleteRole(Data datas) {
        return roleMapper.batchDeleteObj(datas);
	}

    @Override
    public Page<Role> pageQuery(Map<String, Object> paramMap) {
        Page<Role> rolePage = new Page<Role>((Integer) paramMap.get("pageno"), (Integer) paramMap.get("pagesize"));
        paramMap.put("startIndex", rolePage.getStartIndex());
        List<Role> roles = roleMapper.pageQuery(paramMap);
        int count = roleMapper.queryCount(paramMap);
        rolePage.setData(roles);
        rolePage.setTotalsize(count);
        return rolePage;
    }

}
