/**
 * 
 */
package com.soso.constants;

/**
 * ���˿��ײ�����
 * @author Administrator
 *
 */
public enum SuperCardValue {
	//��Ա����
	//ͨ��ʱ��	��������		���ʷ�		��������
	TALKTIME(200),FLOW(1024),ACCOUNT(78),SMSCOUNT(50);
	
	private int value;//��Ӧ��ֵ
	//���췽��
	private SuperCardValue(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	//��дobject��toString����
	public String toString(){
		return this.value + "";
	}
	

}
