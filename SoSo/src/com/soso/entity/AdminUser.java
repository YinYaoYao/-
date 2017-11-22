/**
 * 
 */
package com.soso.entity;

/**
 * 管理员类
 * @author Administrator
 *
 */
public class AdminUser extends User{
	
	public AdminUser(){}
	public AdminUser(String name,String pwd){
		super(name, pwd);
	}
	@Override
	public void showInfo() {
		System.out.println("管理员用户     用户名:"+this.getName()+"\t密码:"+this.getPwd());
		
	}

}
