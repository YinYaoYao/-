/**
 * 
 */
package com.soso.entity;

import com.soso.constants.TalkCardValue;
import com.soso.services.CallService;
import com.soso.services.SendService;

/**
 * 话唠卡
 * 
 * @author Administrator
 * 
 */
public class TalkCard extends CardUser implements CallService,SendService {
	// 属性
	private int talkTime;// 通话时长
	private int smsCount;// 短信条数

	public TalkCard() {
	}

	public TalkCard(String name, String pwd, String phoneNumber,
			double account, double money, int TalkTime, int smsCount) {
		super(name, pwd, phoneNumber, account, money);
		this.talkTime = TalkTime;
		this.smsCount = smsCount;
	}

	public int getTalkTime() {
		return talkTime;
	}

	public void setTalkTime(int talkTime) {
		this.talkTime = talkTime;
	}

	public int getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}

	//显示注册信息
	public void showInfo() {
		System.out.println("话唠卡用户:用户名:" + super.getName() + "\t" + "密码:"
				+ super.getPwd() + "\t" + "手机号:" + super.getPhoneNumber()
				+ "    账户余额:" + super.getMoney() + "元");
	}

	//显示信息
	public void show() {
		System.out.println("类型:话唠卡:通话时长:" + TalkCardValue.TALKTIME.getValue()
				+ "(分钟/月)\t短信" + TalkCardValue.SMSCOUNT.getValue() + "(条/月)"
				+ "\t月资费" + TalkCardValue.ACCOUNT.getValue() + "(元/月)");

	}

	//本月账单查询
	public StringBuffer getAccountDetail() {
		StringBuffer content = new StringBuffer();
		
		content.append("*****本月账单查询******\n");
		content.append("手机号:"+super.getPhoneNumber()+"\t");
		content.append("本月共消费:"+this.getAccount()+"元"+"\t"+"余额:"+this.getMoney()+"元\t");
		content.append("基础套餐:"+TalkCardValue.ACCOUNT+"元");
		return content;
	}

	//套餐余量 
	public StringBuffer getRemainDetail() {
		StringBuffer content = new StringBuffer();
		content.append("*****套餐余量查询******\n");
		content.append("手机号:"+super.getPhoneNumber());
		content.append("通话时长:"+this.getTalkTime()+"分钟");
		content.append("短信条数:"+this.getSmsCount()+"条");
		return content;
	}

	//短信方法实现	
	public void sendMessage(int count) throws Exception {
		if(this.getSmsCount() >= count){
			this.setSmsCount((this.getSmsCount() - count));
		} else {
			double consumeMoney = 0.1*( count - this.getSmsCount() );
			if(this.getMoney() >= consumeMoney){
				this.setMoney(this.getMoney() - consumeMoney);
				this.smsCount = 0;
				this.setAccount(this.getAccount() + consumeMoney);
			} else {
				//否：抛出异常  继续下一轮循环
				throw new Exception("您的余额不足,请充值");
			}
			
		}
		
	}

	//通话方法实现
			public void localCall(int minCount) throws Exception {
				if(this.getTalkTime() > minCount){
					//套餐内剩余的通话时长 = 套餐内的通话时长 - 消耗的通话时长
					this.setTalkTime(this.talkTime -= minCount);
				} else{
					//额外消费金额 = 0.2*(消耗时长 - 套餐内剩余的通话时长)
					double consumeMoney = 0.2*(minCount - this.talkTime);
					 
					// 判断： 当前账户余额>= 额外消费金额
					if(this.getMoney() >= consumeMoney){
						
						//是：当前账户余额＝当前账户余额－额外消费金额
						this.setMoney(this.getMoney() - consumeMoney);
						//套餐内通话时长＝０
						this.talkTime = 0;
						//＝当月消费金额＋额外消费金额
						this.setAccount(this.getAccount() + consumeMoney);
					} else {
						//否：抛出异常  继续下一轮循环
						throw new Exception("您的余额不足,请充值");
					}
					

				}
			}
}
