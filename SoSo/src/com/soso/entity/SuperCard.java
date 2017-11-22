/**
 * 
 */
package com.soso.entity;

import java.text.DecimalFormat;


import com.soso.constants.SuperCardValue;
import com.soso.services.CallService;
import com.soso.services.NetService;
import com.soso.services.SendService;

/**
 * 超人卡
 * 
 * @author Administrator
 * 
 */
public class SuperCard extends CardUser implements CallService,SendService,NetService {
	final int FLOW = 1024;// 定义常量,进行流量的数据转换
	// 格式化器---取一位小数点
	DecimalFormat format = new DecimalFormat("#.0");

	// 属性
	private int TalkTime;// 通话时长
	private int flow;// 上网流量
	private int smsCount;// 短信条数

	public SuperCard() {

	}

	public SuperCard(String name, String pwd, String phoneNumber,
			double account, double money, int talkTime, int smsCount, int flow) {
		super(name, pwd, phoneNumber, account, money);
		TalkTime = talkTime;
		this.smsCount = smsCount;
		this.flow = flow;
	}

	public int getTalkTime() {
		return TalkTime;
	}

	public void setTalkTime(int talkTime) {
		TalkTime = talkTime;
	}

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	public int getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(int smsCount) {
		this.smsCount = smsCount;
	}

	@Override
	public void showInfo() {
		System.out.println("话唠卡用户:用户名:" + super.getName() + "\t" + "密码:"
				+ super.getPwd() + "\t" + "\t手机号:" + super.getPhoneNumber()
				+ "   账户余额:" + super.getMoney() + "元");

	}

	@Override
	public void show() {
		System.out.println("类型:超人卡:通话时长:" + SuperCardValue.TALKTIME.getValue()
				+ "(分钟/月)\t短信" + SuperCardValue.SMSCOUNT.getValue() + "(条/月)"
				+ "\t流量:" + SuperCardValue.FLOW.getValue() + "(MB/月)"
				+ "\t月资费:" + SuperCardValue.ACCOUNT.getValue() + "(元/月)");
	}

	// 本月账单查询
	public StringBuffer getAccountDetail() {
		StringBuffer content = new StringBuffer();
		content.append("*****本月账单查询******\n");
		content.append("手机号:"+this.getPhoneNumber()+"\t");
		content.append("本月共消费:" + this.getAccount() + "元"+"\t"+"余额:"
				+ this.getMoney() + "元\t");
		content.append("基础套餐:" + SuperCardValue.ACCOUNT + "元");
		return content;
	}

	//套餐余量 
		public StringBuffer getRemainDetail() {
			StringBuffer content = new StringBuffer();
			content.append("*****套餐余量查询******\n");
			content.append("手机号:"+this.getPhoneNumber());
			content.append("通话时长:"+this.getTalkTime()+"分钟");
			content.append("短信条数:"+this.getSmsCount()+"条");
			String tempFlow = (this.getFlow() == 0)?"0 GB":format.format(this.getFlow()/FLOW);
			content.append("上网流量:"+tempFlow);
			return content;
		}

		//上网方法实现
		public void netPlay(int flow) throws Exception {
			if(this.getFlow() >= flow){
				this.setFlow(this.getFlow() - flow);
			} else {
				double consumeMoney = 0.1*(flow - this.getFlow() );
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
				this.setTalkTime(this.TalkTime -= minCount);
			} else{
				//额外消费金额 = 0.2*(消耗时长 - 套餐内剩余的通话时长)
				double consumeMoney = 0.2*(minCount - this.TalkTime);
				 
				// 判断： 当前账户余额>= 额外消费金额
				if(this.getMoney() >= consumeMoney){
					
					//是：当前账户余额＝当前账户余额－额外消费金额
					this.setMoney(this.getMoney() - consumeMoney);
					//套餐内通话时长＝０
					this.TalkTime = 0;
					//＝当月消费金额＋额外消费金额
					this.setAccount(this.getAccount() + consumeMoney);
				} else {
					//否：抛出异常  继续下一轮循环
					throw new Exception("您的余额不足,请充值");
				}
				

			}
		}

}
