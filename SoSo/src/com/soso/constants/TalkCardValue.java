package com.soso.constants;
/**
 * ���뿨�ײ�����
 * @author Administrator
 *
 */
public enum TalkCardValue {
		//��Ա����
	//���ʷ�		ͨ��ʱ��			��������	
	ACCOUNT(58),TALKTIME(12),SMSCOUNT(30);
	
	//���췽��,˽���� ,֮��Ͷ������ÿһ�ֵ
	private int value;//��Ӧ��ֵ
	//���췽��
	private TalkCardValue(int value){
		this.value = value; 
	}
	public int getValue() {
		return value;
	}
	
	//��дobject��toString����
	public String toString(){
		return this.value+"";
		
	}
	
	

}
