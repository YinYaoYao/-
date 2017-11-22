/**
 * 
 */
package com.soso.constants;

/**
 * 超人卡套餐数据
 * @author Administrator
 *
 */
public enum SuperCardValue {
	//成员变量
	//通话时长	上网流量		月资费		短信条数
	TALKTIME(200),FLOW(1024),ACCOUNT(78),SMSCOUNT(50);
	
	private int value;//对应的值
	//构造方法
	private SuperCardValue(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	//重写object的toString方法
	public String toString(){
		return this.value + "";
	}
	

}
