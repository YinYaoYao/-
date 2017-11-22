/**
 * 
 */
package com.soso.entity;

import com.soso.constants.TalkCardValue;
import com.soso.services.CallService;
import com.soso.services.SendService;

/**
 * ���뿨
 * 
 * @author Administrator
 * 
 */
public class TalkCard extends CardUser implements CallService,SendService {
	// ����
	private int talkTime;// ͨ��ʱ��
	private int smsCount;// ��������

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

	//��ʾע����Ϣ
	public void showInfo() {
		System.out.println("���뿨�û�:�û���:" + super.getName() + "\t" + "����:"
				+ super.getPwd() + "\t" + "�ֻ���:" + super.getPhoneNumber()
				+ "    �˻����:" + super.getMoney() + "Ԫ");
	}

	//��ʾ��Ϣ
	public void show() {
		System.out.println("����:���뿨:ͨ��ʱ��:" + TalkCardValue.TALKTIME.getValue()
				+ "(����/��)\t����" + TalkCardValue.SMSCOUNT.getValue() + "(��/��)"
				+ "\t���ʷ�" + TalkCardValue.ACCOUNT.getValue() + "(Ԫ/��)");

	}

	//�����˵���ѯ
	public StringBuffer getAccountDetail() {
		StringBuffer content = new StringBuffer();
		
		content.append("*****�����˵���ѯ******\n");
		content.append("�ֻ���:"+super.getPhoneNumber()+"\t");
		content.append("���¹�����:"+this.getAccount()+"Ԫ"+"\t"+"���:"+this.getMoney()+"Ԫ\t");
		content.append("�����ײ�:"+TalkCardValue.ACCOUNT+"Ԫ");
		return content;
	}

	//�ײ����� 
	public StringBuffer getRemainDetail() {
		StringBuffer content = new StringBuffer();
		content.append("*****�ײ�������ѯ******\n");
		content.append("�ֻ���:"+super.getPhoneNumber());
		content.append("ͨ��ʱ��:"+this.getTalkTime()+"����");
		content.append("��������:"+this.getSmsCount()+"��");
		return content;
	}

	//���ŷ���ʵ��	
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
				//���׳��쳣  ������һ��ѭ��
				throw new Exception("��������,���ֵ");
			}
			
		}
		
	}

	//ͨ������ʵ��
			public void localCall(int minCount) throws Exception {
				if(this.getTalkTime() > minCount){
					//�ײ���ʣ���ͨ��ʱ�� = �ײ��ڵ�ͨ��ʱ�� - ���ĵ�ͨ��ʱ��
					this.setTalkTime(this.talkTime -= minCount);
				} else{
					//�������ѽ�� = 0.2*(����ʱ�� - �ײ���ʣ���ͨ��ʱ��)
					double consumeMoney = 0.2*(minCount - this.talkTime);
					 
					// �жϣ� ��ǰ�˻����>= �������ѽ��
					if(this.getMoney() >= consumeMoney){
						
						//�ǣ���ǰ�˻�����ǰ�˻����������ѽ��
						this.setMoney(this.getMoney() - consumeMoney);
						//�ײ���ͨ��ʱ������
						this.talkTime = 0;
						//���������ѽ��������ѽ��
						this.setAccount(this.getAccount() + consumeMoney);
					} else {
						//���׳��쳣  ������һ��ѭ��
						throw new Exception("��������,���ֵ");
					}
					

				}
			}
}
