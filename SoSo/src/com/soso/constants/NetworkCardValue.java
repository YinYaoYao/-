package com.soso.constants;
/**
 * ���濨�ײ�����
 * @author Administrator
 *
 */
public enum NetworkCardValue {
	//��Ա����
	//��������     ���ʷ�
	FLOW(3072),ACCOUNT(68);
	
	private int value;//��Ӧ��ֵ
	//���췽��
	private NetworkCardValue(int value){
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
