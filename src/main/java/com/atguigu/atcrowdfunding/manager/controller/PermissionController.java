package com.atguigu.atcrowdfunding.manager.controller;

import com.atguigu.atcrowdfunding.bean.Permission;
import com.atguigu.atcrowdfunding.manager.service.PermissionService;
import com.atguigu.atcrowdfunding.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Elvis Chen
 * @Date 2019/1/23 1:32
 * @Version 1.0
 **/
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/index")
    public String index() {
        return "permission/index";
    }

    @RequestMapping("/toAdd")
	public String toAdd(){
    	return "permission/add";
	}

	@RequestMapping("/doAdd")
	@ResponseBody
	public Object doAdd(Permission permission){
		AjaxResult result = new AjaxResult();
		try {
			int count = permissionService.savePermission(permission);
			result.setSuccess(count == 1);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("保存许可树数据失败!");
		}
		return result;
	}

	@RequestMapping("/toUpdate")
	public String toUpdate(Integer id, Map map) {
		Permission permission = permissionService.getPermissionById(id);
		map.put("permission", permission);
		return "permission/update";
	}

	@RequestMapping("/doUpdate")
	@ResponseBody
	public Object doUpdate(Permission permission) {
		AjaxResult result = new AjaxResult();
		try {
			int count = permissionService.updatePermission(permission);
			result.setSuccess(count == 1);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改许可树数据失败!");
		}
		return result;
	}

	@RequestMapping("/deletePermission")
	@ResponseBody
	public Object deletePermission(Integer id) {
		AjaxResult result = new AjaxResult();
		try {
			int count = permissionService.deletePermission(id);
			result.setSuccess(count == 1);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("删除许可树数据失败!");
		}

		return result;
	}


    //Demo5 - 用Map集合来查找父,来组合父子关系.减少循环的次数,提高性能
    @RequestMapping("/loadData")
    @ResponseBody
    public Object loadData(){
    	AjaxResult result = new AjaxResult();
    	try {
    		List<Permission> root = new ArrayList<Permission>();
    		List<Permission> childredPermissons =  permissionService.queryAllPermission();
    		
    		Map<Integer,Permission> map = new HashMap<Integer,Permission>();
    		for (Permission innerpermission : childredPermissons) {
    			map.put(innerpermission.getId(), innerpermission);
    		}
    		
    		for (Permission permission : childredPermissons) { 
    			//通过子查找父
    			//子菜单	
    			Permission child = permission ; //假设为子菜单
    			if(child.getPid() == null ){
    				root.add(permission);
      			}else{
    				//父节点
    				Permission parent = map.get(child.getPid());
    				parent.getChildren().add(child);
	    			}
    		}
    		result.setSuccess(true);
    		result.setData(root);

    	} catch (Exception e) {
    		e.printStackTrace();
    		result.setSuccess(false);
    		result.setMessage("加载许可树数据失败!");
    	}
    	return result ;
    }
	private void queryChildPermissions(Permission permission){
		List<Permission> children = permissionService.getChildrenPermissionByPid(permission.getId());
		//组合父子关系
		permission.setChildren(children);		
		
		for (Permission innerChildren : children) {
			queryChildPermissions(innerChildren);
		}
	}  
/*

    //Demo4 - 采用一次性加载所有permission数据,减少与数据的交互次数
    @RequestMapping("/loadData")
    @ResponseBody
    public Object loadData(){
        AjaxResult result = new AjaxResult();

        try{

            List<Permission> root =new ArrayList<Permission>();

            List<Permission> childredPermissions = permissionService.queryAllPermission();
            for (Permission permission : childredPermissions) {
                Permission child = permission;
                if (child.getPid() == null) {
                    root.add(permission);
                } else {
                    for (Permission innerpermission : childredPermissions) {
                        if (child.getPid() == innerpermission.getId()) {
                            Permission parent = innerpermission;
                            parent.getChildren().add(child);
                            break;
                        }
                    }
                }
            }

            result.setSuccess(true);
            result.setData(root);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("加载许可树数据失败!");

        }
        return result;
    }
*/

/*
    //Demo3 - 采用递归调用来解决许可数多个层次的问题
    @RequestMapping("/loadData")
    @ResponseBody
public Object loadData(){
        AjaxResult result =new AjaxResult();

        try{

            List<Permission> root = new ArrayList<>();

            //父
            Permission permission = permissionService.getRootPermission();

            root.add(permission);

            queryChildPermissions(permission);

            result.setSuccess(true);
            result.setData(root);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("加载许可树数据失败!");
        }
        return result;
    }
*/
    




/*    //Demo2-从数据表t_permission查询数据,显示许可树
    @RequestMapping("/loadData")
    @ResponseBody
    public Object loadData() {

        AjaxResult result = new AjaxResult();
        try {

            List<Permission> root = new ArrayList<Permission>();

            //父
            Permission permission = permissionService.getRootPermission();

            root.add(permission);

            //子
            List<Permission> children = permissionService.getChildrenPermissionByPid(permission.getId());

            //设置父子关系
            permission.setChildren(children);

            for (Permission child : children) {
                child.setOpen(true);

                //根据父查找子
                List<Permission> innerChildren = permissionService.getChildrenPermissionByPid(child.getId());

                //设置父子关系
                child.setChildren(innerChildren);
            }

            result.setSuccess(true);
            result.setData(root);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("加载许可树数据失败!");
        }
        return result;
    }*/

    //Demo1 - 模拟数据生成树
/*    @RequestMapping("/loadData")
    @ResponseBody
    public Object loadData() {
        AjaxResult result = new AjaxResult();


        try {

            List<Permission> root = new ArrayList<Permission>();

            //父
            Permission permission = new Permission();
            permission.setName("系统权限菜单");
            permission.setOpen(true);

            root.add(permission);

            //子
            List<Permission> children = new ArrayList<Permission>();

            Permission permission1 = new Permission();
            permission1.setName("控制面板");

            Permission permission2 = new Permission();
            permission2.setName("权限管理");

            children.add(permission1);
            children.add(permission2);

            //设置父子关系
            permission.setChildren(children);

         result.setSuccess(true);
         result.setData(root);

        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("加载许可树数据失败!");
        }

        *//**
         * {"success":true,"message":null,"page":null,
         * "data":[{"id":null,"pid":null,"name":"系统权限菜单","icon":null,
         * "url":null,"open":true,"children":[{"id":null,"pid":null,"name":"控制面板",
         * "icon":null,"url":null,"open":false,"children":null},{"id":null,"pid":null,"name":"权限管理",
         * "icon":null,"url":null,"open":false,"children":null}]}]}
         *//*

        return result;
    }*/


}
