/**
 * 
 */
package com.soso.entity;

import java.text.DecimalFormat;

import com.soso.constants.NetworkCardValue;
import com.soso.services.NetService;


/**
 * ���濨
 * 
 * @author Administrator
 * 
 */
public class NetworkCard extends CardUser implements NetService {
	final int FLOW = 1024;// ���峣��,��������������ת��
	// ��ʽ����---ȡһλС����
	DecimalFormat format = new DecimalFormat("#.0");

	// ����
	private int flow;// ��������(MB)

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
		System.out.println("���뿨�û�:�û���:" + super.getName()+"\t" + "����:"
				+ super.getPwd()+"\t" + "�ֻ���:" + super.getPhoneNumber() + "  �˻����:"
				+ super.getMoney() + "Ԫ");

	}

	@Override
	public void show() {
		System.out.println("����:���濨:����" + NetworkCardValue.FLOW.getValue() + "(MB/��)" + "\t���ʷ�"
				+ NetworkCardValue.ACCOUNT.getValue() + "(Ԫ/��)");

	}
	//�����˵���ѯ
	public StringBuffer getAccountDetail() {
		StringBuffer content = new StringBuffer();
		content.append("*****�����˵���ѯ******\n");
		content.append("�ֻ���:"+this.getPhoneNumber()+"\t");
		content.append("���¹�����:"+this.getAccount()+"Ԫ\t���:"+this.getMoney()+"Ԫ\t");
		content.append("�����ײ�:"+NetworkCardValue .ACCOUNT+"Ԫ");
		return content;
	}

	//�ײ����� 
	public StringBuffer getRemainDetail() {
		StringBuffer content = new StringBuffer();
		content.append("�ֻ���:"+this.getPhoneNumber());
		content.append("*****�ײ�������ѯ******\n");
		content.append("��������:"+format.format(this.getFlow()/FLOW)+"GB");
		return content;
	}

	//��������ʵ��
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
						//���׳��쳣  ������һ��ѭ��
						throw new Exception("��������,���ֵ");
					}
					
				}
				
			}
}
