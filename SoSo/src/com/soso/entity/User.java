/**
 * 
 */
package com.soso.entity;

/**
 * �û���
 * @author Administrator
 *
 */
public abstract class User { 
	//����
	private String name;//�û���
	private String pwd;//����
	
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
	//��ʾ��Ϣ(���󷽷�)
	public abstract void showInfo();
	

}
