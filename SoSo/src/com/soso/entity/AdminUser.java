/**
 * 
 */
package com.soso.entity;

/**
 * ����Ա��
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
		System.out.println("����Ա�û�     �û���:"+this.getName()+"\t����:"+this.getPwd());
		
	}

}
