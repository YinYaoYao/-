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
 * ���˿�
 * 
 * @author Administrator
 * 
 */
public class SuperCard extends CardUser implements CallService,SendService,NetService {
	final int FLOW = 1024;// ���峣��,��������������ת��
	// ��ʽ����---ȡһλС����
	DecimalFormat format = new DecimalFormat("#.0");

	// ����
	private int TalkTime;// ͨ��ʱ��
	private int flow;// ��������
	private int smsCount;// ��������

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
		System.out.println("���뿨�û�:�û���:" + super.getName() + "\t" + "����:"
				+ super.getPwd() + "\t" + "\t�ֻ���:" + super.getPhoneNumber()
				+ "   �˻����:" + super.getMoney() + "Ԫ");

	}

	@Override
	public void show() {
		System.out.println("����:���˿�:ͨ��ʱ��:" + SuperCardValue.TALKTIME.getValue()
				+ "(����/��)\t����" + SuperCardValue.SMSCOUNT.getValue() + "(��/��)"
				+ "\t����:" + SuperCardValue.FLOW.getValue() + "(MB/��)"
				+ "\t���ʷ�:" + SuperCardValue.ACCOUNT.getValue() + "(Ԫ/��)");
	}

	// �����˵���ѯ
	public StringBuffer getAccountDetail() {
		StringBuffer content = new StringBuffer();
		content.append("*****�����˵���ѯ******\n");
		content.append("�ֻ���:"+this.getPhoneNumber()+"\t");
		content.append("���¹�����:" + this.getAccount() + "Ԫ"+"\t"+"���:"
				+ this.getMoney() + "Ԫ\t");
		content.append("�����ײ�:" + SuperCardValue.ACCOUNT + "Ԫ");
		return content;
	}

	//�ײ����� 
		public StringBuffer getRemainDetail() {
			StringBuffer content = new StringBuffer();
			content.append("*****�ײ�������ѯ******\n");
			content.append("�ֻ���:"+this.getPhoneNumber());
			content.append("ͨ��ʱ��:"+this.getTalkTime()+"����");
			content.append("��������:"+this.getSmsCount()+"��");
			String tempFlow = (this.getFlow() == 0)?"0 GB":format.format(this.getFlow()/FLOW);
			content.append("��������:"+tempFlow);
			return content;
		}

		//��������ʵ��
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
					//���׳��쳣  ������һ��ѭ��
					throw new Exception("��������,���ֵ");
				}
				
			}
			
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
				this.setTalkTime(this.TalkTime -= minCount);
			} else{
				//�������ѽ�� = 0.2*(����ʱ�� - �ײ���ʣ���ͨ��ʱ��)
				double consumeMoney = 0.2*(minCount - this.TalkTime);
				 
				// �жϣ� ��ǰ�˻����>= �������ѽ��
				if(this.getMoney() >= consumeMoney){
					
					//�ǣ���ǰ�˻�����ǰ�˻����������ѽ��
					this.setMoney(this.getMoney() - consumeMoney);
					//�ײ���ͨ��ʱ������
					this.TalkTime = 0;
					//���������ѽ��������ѽ��
					this.setAccount(this.getAccount() + consumeMoney);
				} else {
					//���׳��쳣  ������һ��ѭ��
					throw new Exception("��������,���ֵ");
				}
				

			}
		}

}
