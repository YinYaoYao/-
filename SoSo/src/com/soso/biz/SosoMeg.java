/**
 * 
 */
package com.soso.biz;

import java.util.Scanner;

import com.soso.constants.NetworkCardValue;
import com.soso.constants.SuperCardValue;
import com.soso.constants.TalkCardValue;
import com.soso.entity.AdminUser;
import com.soso.entity.CardUser;
import com.soso.entity.NetworkCard;
import com.soso.entity.SuperCard;
import com.soso.entity.TalkCard;
import com.soso.entity.User;
import com.soso.utils.UserUtils;

/**
 * ��ҵ����
 * 
 * @author Administrator
 * 
 */
public class SosoMeg {
	Scanner input = new Scanner(System.in);// �������༶,����ȫ��ʹ��
	UserUtils utils = new UserUtils();
	public static void main(String[] args) {
		SosoMeg soso = new SosoMeg();
		soso.start();
	}

	/**
	 * ������(�����)
	 */
	public void start() {
		int menuChoose = 0;
		int typeChoose = 0;
		int UserChoose = 0;
		//���ݳ�ʼ��--�����û�
		utils.inti();
		// ʹ�÷�֧���,����ѡ����ִ����Ӧ����
		do {
			System.out.println("***********��ӭʹ�����ƶ�����***************");
			System.out.println("1:ע�����û�     2:��¼��     3:�ʷ�˵��     4:�˳�ϵͳ");
			System.out.print("��ѡ��:");
			menuChoose = input.nextInt();
			switch (menuChoose) {
			case 1:
				User newUser = null;// ����User������Ϊ��,�洢���ɵ��û�
				int brandChoose = 0;
				do{
				// ע�����û�
				System.out.println("*****ע�����û�******");
				System.out.print("���ƶ��û�\n");
					// ִ�д������ƶ��û�
					System.out.print("1:���뿨   2:���濨   3:���˿�\n��ѡ���ײ�");
					 brandChoose = input.nextInt();
					// ����ע���û�����
				}while(brandChoose > 3);
					newUser = registCardUser(brandChoose);
					utils.addUserToUserList(newUser);
					System.out.println("********ע��ɹ�*********");
					newUser.showInfo();
				break;
			case 2:
				boolean bool = false;//��־�Ƿ��˳���¼�ಲ˵�
				do {
					// ��¼����
					
					// �����û���������,�ж��������û�
					System.out.print("�������û���:");
					String name = input.next();
					System.out.print("����������:");
					String pwd = input.next();
					//��ȡ�û�����
					typeChoose = utils.getTypeUser(name, pwd);					//
					if (typeChoose == 1) {
						int b = 0;
						do {
							System.out.println("*****����Ա�˵�*****");
							System.out.print("1:��ѯ�û���Ϣ   2:�����û�����\n��ѡ��:");
							typeChoose = input.nextInt();
							if (typeChoose == 1) {
								// ��ѯ�û���Ϣ
								System.out.println("******��ѯ�û���Ϣ*******");
								utils.showUsers();
								bool = true;
							
							} else if (typeChoose == 2) {
								// �����û�����
								System.out.println("******�����û�����*******");
								System.out.print("�������ֻ���");
								String phoneNumber = input.next();
								utils.removeUser(phoneNumber);
							}  else if (typeChoose > 2){							
								bool = false;
							} 
						} while (b < 2);
						break;//�˳���¼�ಲ˵�
					} else if (typeChoose == 2) {
						do {
							System.out.println("*****���û��˵�*****");
							System.out.println("1:�������Ѽ�¼");
							System.out.println("2:�ײ�������ѯ");
							System.out.println("3:���ѳ�ֵ");
							System.out.println("4:ʹ����");
							System.out.println("5:�����ײ�");
							System.out.println("6:���Ѽ�¼");
							System.out.print("��ѡ��:");
							UserChoose = input.nextInt();
							switch (UserChoose) {
							case 1:
								// �����˵�
								System.out.println("*******�����˵���ѯ******");
							
								utils.showAccountDetail(name, pwd);
								break;
							case 2:
								// �ײ�����
								System.out.println("******�ײ�������ѯ******");
							
								utils.showRemainDetail(name, pwd);
								break;
							case 3:
								// ���ѳ�ֵ
								System.out.println("********��ֵ����*********");
								System.out.println("�������ֻ���");
								String phoneNumber = input.next();
								System.out.println("�������ֵ���"); 	
								double rechargeMoney = input.nextDouble();
								utils.rechargeMoney(phoneNumber, rechargeMoney);
								break;
							case 4:
								// ʹ����
								System.out.println("******ʹ����*******");
								try {
									utils.useSOSO(name,pwd);
								} catch (Exception e) {
									
									e.printStackTrace();
									continue;
								}
								break;
							case 5:
								System.out.print("1:���뿨   2:���濨   3:���˿�\n��ѡ���ײ�");
								int num = input.nextInt();
								utils.changingPack( name,  pwd,  num);
								break;
							case 6:
								
								utils.prinConsumInfo();
								break;
							default:
								break;
							}
						} while (UserChoose >= 1 && UserChoose <= 5);
						break;//�˳����û��˵��˵�
					} else if(typeChoose == 0) {
						System.out.println("�޴��û�");
						return;
					} else {
						bool = true;
						
					}
				} while (bool);
				break;//�˳���¼�ಲ˵�
			case 3:
				// �ʷ�˵��
				utils.showDescription();
				break;
			case 4:
				// �˳�ϵͳ
				System.out.println("�˳�ϵͳ");
				break;
			default:
				// �������˵�
				break;
			}
		} while (menuChoose != 4);
	}

	/**
	 * ע�����Ա�û�
	 */
	public User registAdminUser() {
		System.out.print("�������û�����");
		String name = input.next();
		System.out.print("���������룺");
		String pwd = input.next();
		User adminUser = new AdminUser(name, pwd);
		return adminUser;
	}

	/**
	 * ע�����ƶ��û� ���� Ʒ���ײͱ�� ����ֵ ���ƶ�����
	 */
	public CardUser registCardUser(int type) {
		CardUser cardUser = null;
		TalkCard tc = new TalkCard();
		NetworkCard nc = new NetworkCard();
		SuperCard sc = new SuperCard();

		if (type == 1) {
			tc.show();
		} else if (type == 2) {
			nc.show();
		} else if (type == 3) {
			sc.show();
		}

		// �ṩ�Ÿ���ѡ��ĺ���
		String[] numbers = utils.getPhoneNumber(9);
		System.out.println("\t\t     ******��ѡ��Ŀ���********");
		for (int i = 0; i < numbers.length; i++) {
			System.out.print((i + 1) + "." + numbers[i] + "\t\t");
			if ((i + 1) % 3 == 0) {
				System.out.println();
			}
		}
		System.out.print("��ѡ�񿨺ű��(1~9)");
		String phoneNumber = numbers[input.nextInt() - 1];
		System.out.print("�������û�����");
		String name = input.next();
		System.out.print("���������룺");
		String pwd = input.next();
		boolean bool = false;
		do {
			System.out.println("������Ԥ��Ľ��");
			double money = input.nextDouble();
			// ͨ���û�Ʒ�Ʊ���������ƶ��û�����
			switch (type) {
			case 1:
				cardUser = new TalkCard(name, pwd, phoneNumber,
						TalkCardValue.ACCOUNT.getValue(), money
								- TalkCardValue.ACCOUNT.getValue(),
						TalkCardValue.TALKTIME.getValue(),
						TalkCardValue.SMSCOUNT.getValue());

				break;
			case 2:
				// ���濨
				cardUser = new NetworkCard(name, pwd, phoneNumber,
						NetworkCardValue.ACCOUNT.getValue(), money
								- NetworkCardValue.ACCOUNT.getValue(),
						NetworkCardValue.FLOW.getValue());
				break;
			case 3:
				// ���˿�
				cardUser = new SuperCard(name, pwd, phoneNumber,
						SuperCardValue.ACCOUNT.getValue(), money
								- SuperCardValue.ACCOUNT.getValue(),
						SuperCardValue.TALKTIME.getValue(),
						SuperCardValue.SMSCOUNT.getValue(),
						SuperCardValue.FLOW.getValue());
				break;
			}
			if (money < cardUser.getAccount()) {
				System.out.println("����Ԥ������֧�����¹̶��ײ��ʷ�,�����³�ֵ");
				bool = true;
			} else {
				bool = false;
			}
		} while (bool);

		return cardUser;

	}

}
