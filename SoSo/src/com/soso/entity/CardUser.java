/**
 * 
 */
package com.soso.entity;

/**
 * ���û���
 * 
 * @author Administrator
 * 
 */
public abstract class CardUser extends User{
	// ����

	private String phoneNumber;// �ֻ�����
	private double account;// �������ѽ��
	private double money;// �˻����

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
	//�����˵���ѯ
	public abstract StringBuffer getAccountDetail();
	//�ײ�������ѯ
	public abstract StringBuffer getRemainDetail();
	//���ѳ�ֵ
	public void recharge(double rechargeMoney){
		this.money += rechargeMoney;
	}

	

}
