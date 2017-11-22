/**
 * 
 */
package com.soso.entity;

/**
 * 嗖嗖用户类
 * 
 * @author Administrator
 * 
 */
public abstract class CardUser extends User{
	// 属性

	private String phoneNumber;// 手机卡号
	private double account;// 本月消费金额
	private double money;// 账户余额

	public CardUser() {
	}

	public CardUser(String name, String pwd, String phoneNumber,
			double account, double money) {
		super(name, pwd);
		this.phoneNumber = phoneNumber;
		this.account = account;
		this.money = money;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public void setAccount(double account) {
		this.account = account;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public double getAccount() {
		return account;
	}
	public abstract void show();
	//本月账单查询
	public abstract StringBuffer getAccountDetail();
	//套餐余量查询
	public abstract StringBuffer getRemainDetail();
	//话费充值
	public void recharge(double rechargeMoney){
		this.money += rechargeMoney;
	}

	

}
