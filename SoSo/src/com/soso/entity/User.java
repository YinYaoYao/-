/**
 * 
 */
package com.soso.entity;

/**
 * 用户类
 * @author Administrator
 *
 */
public abstract class User { 
	//属性
	private String name;//用户名
	private String pwd;//密码
	
	public User(){}
	public User(String name,String pwd){
		this.name = name;
		this.pwd = pwd;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	//显示信息(抽象方法)
	public abstract void showInfo();
	

}
