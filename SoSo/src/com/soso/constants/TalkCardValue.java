package com.soso.constants;
/**
 * 话唠卡套餐数据
 * @author Administrator
 *
 */
public enum TalkCardValue {
		//成员变量
	//月资费		通话时长			短信条数	
	ACCOUNT(58),TALKTIME(12),SMSCOUNT(30);
	
	//构造方法,私有型 ,之后就对上面的每一项赋值
	private int value;//对应的值
	//构造方法
	private TalkCardValue(int value){
		this.value = value; 
	}
	public int getValue() {
		return value;
	}
	
	//重写object的toString方法
	public String toString(){
		return this.value+"";
		
	}
	
	

}
