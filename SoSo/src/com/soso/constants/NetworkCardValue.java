package com.soso.constants;
/**
 * 网虫卡套餐数据
 * @author Administrator
 *
 */
public enum NetworkCardValue {
	//成员变量
	//上网流量     月资费
	FLOW(3072),ACCOUNT(68);
	
	private int value;//对应的值
	//构造方法
	private NetworkCardValue(int value){
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
