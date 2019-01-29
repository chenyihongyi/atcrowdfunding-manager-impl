package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.bean.Role;
import com.atguigu.atcrowdfunding.controller.BaseController;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.manager.service.RoleService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import com.atguigu.atcrowdfunding.util.Page;
import com.atguigu.atcrowdfunding.util.StringUtil;
import com.atguigu.atcrowfunding.vo.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Elvis Chen
 * @Date 2019/1/21 23:05
 * @Version 1.0
 **/
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index(){
        return "role/index";
    }

    @RequestMapping("/add")
    public String add(){
        return "role/add";
    }

    @RequestMapping("/assignPermission")
    public String assignPermission(){

        return "role/assignPermission";
    }

    @RequestMapping("/loadDataAsync")
    @ResponseBody
    public Object loadDataAsync() {

        List<Permission> root = new ArrayList<Permission>();

        List<Permission> childredPermissons = permissionService.queryAllPermission();

        Map<Integer, Permission> map = new HashMap<Integer, Permission>();

        for (Permission innerpermission : childredPermissons) {
            map.put(innerpermission.getId(), innerpermission);
        }
        for (Permission permission : childredPermissons) {
            Permission child = permission;
            if (child.getPid() == null) {
                root.add(permission);
            } else {
                Permission parent = map.get(child.getPid());
                parent.getChildren().add(child);
            }
        }
        return root;
    }

    @RequestMapping("/doAssignPermission")
    @ResponseBody
    public Object doAssignPermission(Integer roleid, Data datas) {
        AjaxResult result = new AjaxResult();
        try {
            int count = roleService.saveRolePermissionRelationship(roleid, datas);
            result.setSuccess(count==datas.getIds().size());
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    @RequestMapping("/batchDelete")
    @ResponseBody
    public Object batchDelete(Data datas) {
        AjaxResult result = new AjaxResult();
        try {
            int count = roleService.batchDeleteRole(datas);
            if (count == datas.getDatas().size()) {
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
    public Object delete(Integer uid) {
        AjaxResult result = new AjaxResult();
        try {
            int count = roleService.deleteRole(uid);
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

    @RequestMapping("/doEdit")
    @ResponseBody
    public Object doEdit(Role role) {
        AjaxResult result = new AjaxResult();
        try {
            int count = roleService.updateRole(role);
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

    @RequestMapping("/edit")
    public String edit(Integer id, Map<String, Object> map) {
        Role role = roleService.getRole(id);
        map.put("role", role);
        return "role/edit";
    }

    @RequestMapping("/doAdd")
    @ResponseBody
    public Object doAdd(Role role) {
        AjaxResult result = new AjaxResult();
        try {
            roleService.saveRole(role);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

	@RequestMapping("/pageQuery")
	@ResponseBody
	public Object pageQuery(String queryText,@RequestParam(required = false, defaultValue = "1") Integer pageno,
			@RequestParam(required = false, defaultValue = "2") Integer pagesize){
		AjaxResult result = new AjaxResult();
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("pageno", pageno); // 空指针异常
			paramMap.put("pagesize", pagesize);
			
			if(StringUtil.isNotEmpty(queryText)){
				queryText = queryText.replaceAll("%", "\\\\%"); 
				System.out.println("--------------"+queryText);
			}
			
			paramMap.put("queryText", queryText);
			
			// 分页查询数据
			Page<Role> rolePage = roleService.pageQuery(paramMap);

			result.setPage(rolePage);
			result.setSuccess(true);
		} catch (Exception e) {			
			e.printStackTrace();
			result.setSuccess(false);
		}
		return  result;
	}
}