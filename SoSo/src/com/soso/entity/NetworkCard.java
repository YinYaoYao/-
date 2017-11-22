/**
 * 
 */
package com.soso.entity;

import java.text.DecimalFormat;

import com.soso.constants.NetworkCardValue;
import com.soso.services.NetService;


/**
 * 网虫卡
 * 
 * @author Administrator
 * 
 */
public class NetworkCard extends CardUser implements NetService {
	final int FLOW = 1024;// 定义常量,进行流量的数据转换
	// 格式化器---取一位小数点
	DecimalFormat format = new DecimalFormat("#.0");

	// 属性
	private int flow;// 上网流量(MB)

	public NetworkCard() {

	}

	public NetworkCard(String name, String pwd, String phoneNumber,
			double account, double money, int flow) {
		super(name, pwd, phoneNumber, account, money);
		this.flow = flow;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	@Override
	public void showInfo() {
		System.out.println("话唠卡用户:用户名:" + super.getName()+"\t" + "密码:"
				+ super.getPwd()+"\t" + "手机号:" + super.getPhoneNumber() + "  账户余额:"
				+ super.getMoney() + "元");

	}

	@Override
	public void show() {
		System.out.println("类型:网虫卡:流量" + NetworkCardValue.FLOW.getValue() + "(MB/月)" + "\t月资费"
				+ NetworkCardValue.ACCOUNT.getValue() + "(元/月)");

	}
	//本月账单查询
	public StringBuffer getAccountDetail() {
		StringBuffer content = new StringBuffer();
		content.append("*****本月账单查询******\n");
		content.append("手机号:"+this.getPhoneNumber()+"\t");
		content.append("本月共消费:"+this.getAccount()+"元\t余额:"+this.getMoney()+"元\t");
		content.append("基础套餐:"+NetworkCardValue .ACCOUNT+"元");
		return content;
	}

	//套餐余量 
	public StringBuffer getRemainDetail() {
		StringBuffer content = new StringBuffer();
		content.append("手机号:"+this.getPhoneNumber());
		content.append("*****套餐余量查询******\n");
		content.append("上网流量:"+format.format(this.getFlow()/FLOW)+"GB");
		return content;
	}

	//上网方法实现
			public void netPlay(int flow) throws Exception {
				if(this.getFlow() >= flow){
					this.setFlow(this.getFlow() - flow);
				} else {
					double consumeMoney = 0.1*( flow - this.getFlow() - flow);
					if(this.getMoney() >= consumeMoney){
						this.setMoney(this.getMoney() - consumeMoney);
						this.flow = 0;
						this.setAccount(this.getAccount() + consumeMoney);
					} else {
						//否：抛出异常  继续下一轮循环
						throw new Exception("您的余额不足,请充值");
					}
					
				}
				
			}
}
