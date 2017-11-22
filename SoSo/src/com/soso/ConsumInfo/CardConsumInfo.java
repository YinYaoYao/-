/**
 * 
 */
package com.soso.ConsumInfo;

/**
 * 消费记录
 * @author Administrator
 *
 */
public class CardConsumInfo {
	
	private String cardNumber;//消费卡号
	private String type;//消费类型
	private  int consumData;//消费数据
	
	
	
	public CardConsumInfo() {
		
	}
	public CardConsumInfo(String cardNumber,String type, int consumData) {
		this.cardNumber = cardNumber;
		this.type = type;
		this.consumData = consumData;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getConsumData() {
		return consumData;
	}
	public void setConsumData(int consumData) {
		this.consumData = consumData;
	}
	
	

}
