/**
 * 
 */
package com.soso.ConsumInfo;

/**
 * ���Ѽ�¼
 * @author Administrator
 *
 */
public class CardConsumInfo {
	
	private String cardNumber;//���ѿ���
	private String type;//��������
	private  int consumData;//��������
	
	
	
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
