/**
 * 
 */
package com.soso.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.soso.ConsumInfo.CardConsumInfo;
import com.soso.constants.NetworkCardValue;
import com.soso.constants.SuperCardValue;
import com.soso.constants.TalkCardValue;
import com.soso.entity.AdminUser;
import com.soso.entity.CardUser;
import com.soso.entity.NetworkCard;
import com.soso.entity.SuperCard;
import com.soso.entity.TalkCard;
import com.soso.entity.User;
import com.soso.services.CallService;
import com.soso.services.NetService;
import com.soso.services.SendService;

/**
 * ������(������)
 * 
 * @author Administrator
 * 
 */
public class UserUtils {
	
	Scanner input = new Scanner(System.in);
	// �����û��б�:(����Ա�����û�)
	List<User> users = new ArrayList<User>();
	// �������Ѽ�¼�б�
	Map<String, List<CardConsumInfo>> consumInfos = new HashMap<String, List<CardConsumInfo>>();
	List<CardConsumInfo> infos = new ArrayList<CardConsumInfo>();
	final int FLOW = 1024;// ���峣��,��������������ת��
	// ��ʽ����---ȡһλС����
	DecimalFormat format = new DecimalFormat("#.0");

	// ��ʼ������
	public void inti() {
		// ����һ������Ա
		User u1 = new AdminUser("admin", "123");
		// ����һ�����뿨�û�
		User u2 = new TalkCard("TalkCard", "123", "177-8888-9999",
				TalkCardValue.ACCOUNT.getValue(), 200,
				TalkCardValue.TALKTIME.getValue(),
				TalkCardValue.SMSCOUNT.getValue());
		// ����һ�����濨�û�
		User u3 = new NetworkCard("NetCard", "12345", "188-0000-9999",
				NetworkCardValue.ACCOUNT.getValue(), 300,
				NetworkCardValue.FLOW.getValue());
		// ����һ�����˿��û�
		User u4 = new SuperCard("SuperCard", "12345", "170-1234-5678",
				SuperCardValue.ACCOUNT.getValue(), 400,
				SuperCardValue.TALKTIME.getValue(),
				SuperCardValue.SMSCOUNT.getValue(),
				SuperCardValue.FLOW.getValue());
		users.add(u1);
		users.add(u2);
		users.add(u3);
		users.add(u4);

	}

	// ��������(��139��ͷ,11λ��)
	public String createNumber() {
		Random random = new Random();
		boolean isExist = false;// ��¼�����û��Ƿ���ڴ˿���
		int temp = 0;// �洢��λ�����
		String phoneNumber = "";// �洢���ɵĿ���
		do {
			do {
				// ���ɰ�λ�����
				temp = random.nextInt(99999999);
				// ����С��10000000
			} while (temp < 10000000);
			// ����֮ǰ,ǰ�����139
			phoneNumber = "139" + temp;
			// ׷��һ���ַ�(-)
			StringBuffer sb = new StringBuffer(phoneNumber);
			for (int j = sb.length() - 4; j > 0; j -= 4) {
				sb.insert(j, '-');
				phoneNumber = sb.toString();
			}
			// �������û��Ŀ������Ƚ�,�����ظ�
			for (User user : users) {
				// �����һ�����û�,�ͽ��бȽϿ���
				if (user instanceof CardUser) {
					// ����ǿ��ת��
					CardUser cardUser = (CardUser) user;
					if (cardUser.getPhoneNumber().equals(phoneNumber)) {
						isExist = true;
					}
				}
			}
		} while (isExist);
		return phoneNumber;
	}

	// ����ָ�������Ŀ����б�
	public String[] getPhoneNumber(int count) {
		String[] numbers = new String[count];
		for (int i = 0; i < count; i++) {
			numbers[i] = createNumber();

		}
		return numbers;
	}

	// �򼯺�������û�
	public void addUserToUserList(User user) {
		users.add(user);
	}

	// �ж��û������Ա������
	public int getTypeUser(String name, String pwd) {
		int type = 0;// 0:�޴��û���1:����Ա�û���2:���û�
		// ��������
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);// ȡ�������е�����
			// ���Դ�д�����ж�
			if (user.getName().equalsIgnoreCase(name)
					&& user.getPwd().equalsIgnoreCase(pwd)) {
				// �ж��û�����
				if (user instanceof AdminUser) {
					type = 1;
				} else {
					type = 2;
				}

			}
		}
		return type;
	}

	// �ʷ�˵��
	public void showDescription() {
		// I/O��д�ʷ�˵��
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("C:\\Users\\Administrator\\Desktop\\�ʷ�˵��.txt");
			br = new BufferedReader(fr);
			// ��ȡ����
			String line = br.readLine();
			while (line != null) {
				System.out.println(line);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					br.close();
				}

				if (br != null) {
					br.close();
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}

	// ��·Ա����--��ѯ�����û���Ϣ
	public void showUsers() {
		StringBuffer adminStr = new StringBuffer("******����Ա�û�******\n");// �洢���й���Ա��Ϣ
		StringBuffer cardStr = new StringBuffer("******���û�********\n");// �洢�����û���Ϣ
		StringBuffer temp = new StringBuffer();// ��ʱ�洢һ����Ϣ��Ϣ
		int adminCount = 0;// ����Ա������
		int cardCount = 0;// �û�������
		// ��������
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			// ����ַ���������
			temp.delete(0, temp.length());
			User user = it.next();
			// �洢��Ϣ
			temp.append(user.getName() + "\t" + user.getPwd() + "\t");
			// ���������ж�
			if (user instanceof AdminUser) {
				adminCount++;
				adminStr.append(adminCount + ".����Ա\n" + temp + "\n");
			} else if (user instanceof TalkCard) {
				// ���뿨
				cardCount++;
				temp.append("�ֻ���:" + ((TalkCard) user).getPhoneNumber() + "\t");
				temp.append("ͨ��ʱ��:" + ((TalkCard) user).getTalkTime() + "����\t");
				temp.append("��������:" + ((TalkCard) user).getSmsCount() + "��\t");
				temp.append("���ʷ�:" + ((TalkCard) user).getAccount() + "Ԫ\t");
				temp.append("�˻����:" + ((TalkCard) user).getMoney() + "Ԫ\t\n");
				cardStr.append(cardCount + ":\t���뿨:" + temp);
			} else if (user instanceof NetworkCard) {
				// ���濨
				cardCount++;
				temp.append("�ֻ���:" + ((NetworkCard) user).getPhoneNumber()
						+ "\t");
				temp.append("��������:"
						+ format.format(((NetworkCard) user).getFlow() * 1.0
								/ FLOW) + "GB\t");
				temp.append("���ʷ�:" + ((NetworkCard) user).getAccount() + "Ԫ\t");
				temp.append("�˻����:" + ((NetworkCard) user).getMoney() + "Ԫ\t\n");
				cardStr.append(cardCount + ":\t���濨:" + temp);

			} else if (user instanceof SuperCard) {
				// ���˿�
				cardCount++;
				temp.append("�ֻ���:" + ((SuperCard) user).getPhoneNumber() + "\t");
				temp.append("ͨ��ʱ��:" + ((SuperCard) user).getTalkTime() + "����\t");
				temp.append("��������:" + ((SuperCard) user).getSmsCount() + "��\t");
				temp.append("��������:"
						+ format.format(((SuperCard) user).getFlow() * 1.0
								/ FLOW) + "GB\t");
				temp.append("���ʷ�:" + ((SuperCard) user).getAccount() + "Ԫ\t");
				temp.append("�˻����:" + ((SuperCard) user).getMoney() + "Ԫ\t\n");
				cardStr.append(cardCount + ":\t���˿�:" + temp);
			}
		}
		System.out.println(adminStr);
		System.out.println(cardStr);

	}

	// ��������
	public void removeUser(String phoneNumber) {
		boolean isdel = false;// ��¼��ɾ��
		// ��������
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User user = it.next();
			if (user instanceof CardUser) {
				CardUser cardUser = (CardUser) user;
				if (cardUser.getPhoneNumber().equals(phoneNumber)) {
					// �ҵ�ɾ��Ԫ��
					it.remove();
					isdel = true;
					System.out.println("�ֻ���Ϊ" + phoneNumber + "���û����������ɹ�");
					break;
				}

			}
		}
		if (!isdel) {
			System.out.println("û���ҵ����û�");
		}
	}

	// �û������˵���ѯ
	public void showAccountDetail(String name, String pwd) {
		for (int i = 0; i < users.size(); i++) {
			// ��������ƶ��û�,�����û������������
			if (users.get(i) instanceof CardUser
					&& users.get(i).getName().equalsIgnoreCase(name)
					&& users.get(i).getPwd().equalsIgnoreCase(pwd)) {
				CardUser cardUser = (CardUser) users.get(i);
				System.out.println(cardUser.getAccountDetail());

			}

		}
	}

	// �û�������ѯ
	public void showRemainDetail(String name, String pwd) {
		for (int i = 0; i < users.size(); i++) {
			// ��������ƶ��û�,�����û������������
			if (users.get(i) instanceof CardUser
					&& users.get(i).getName().equalsIgnoreCase(name)
					&& users.get(i).getPwd().equalsIgnoreCase(pwd)) {
				CardUser cardUser = (CardUser) users.get(i);
				System.out.println(cardUser.getRemainDetail());

			}

		}
	}

	// ���ѳ�ֵ
	public void rechargeMoney(String phoneNumber, double rechargeMoney) {
		boolean isFind = false;
		// �Ӽ����в��Ҵ���������û�
		for (int i = 0; i < users.size(); i++) {

			if (users.get(i) instanceof CardUser) {
				CardUser cardUser = (CardUser) users.get(i);
				if (cardUser.getPhoneNumber().equals(phoneNumber)) {
					isFind = true;
					cardUser.recharge(rechargeMoney);
					System.out.println("��ֵ�ɹ�:����Ϊ" + cardUser.getPhoneNumber()
							+ "\t��ǰ���Ϊ" + cardUser.getMoney() + "Ԫ");
					break;
				}

			}
		}
		if (!isFind) {
			System.out.println("�޴��û�");
		}

	}

	// �����û���-���� ���һ����������û�,������
	public CardUser getUser(String name, String pwd) {
		CardUser reUser = null;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getName().equalsIgnoreCase(name)
					&& users.get(i).getPwd().equalsIgnoreCase(pwd)) {
				reUser = (CardUser) users.get(i);
				break;
			}
		}
		return reUser;
	}

	// ʹ����
	public void useSOSO(String name, String pwd) throws Exception {
		User user = getUser(name, pwd);
		// ����1~6֮��������
		Random random = new Random();
		int number = 0;
		do {
			number = random.nextInt(6) + 1;
			switch (number) {
			case 1:
				// �鿴΢������Ȧ,ʹ������30MB �����û�:���濨 ���˿�
				if (!(user instanceof TalkCard)) {
					// ִ����������
					System.out.println("�鿴΢������Ȧ,ʹ������30MB ");
					NetService netUser = (NetService) user;
					// ʹ�ýӿ�����,������������
					netUser.netPlay(30);
					for (int i = 0; i < users.size(); i++) {

						CardUser cu = (CardUser) user;
						String type = "����";
						int consumData = 30;
						CardConsumInfo info = new CardConsumInfo(
								cu.getPhoneNumber(), type, consumData);
						infos.add(info);

					}
					break;
				} else {
					continue;
				}

			case 2:
				// �����ֻ����߿�����,������˯����,ʹ������2GB �����û�:���濨 ���˿�
				if (!(user instanceof TalkCard)) {
					// ִ����������
					System.out.println("�����ֻ����߿�����,������˯����,ʹ������2GB ");
					NetService netUser = (NetService) user;
					// ʹ�ýӿ�����,������������
					netUser.netPlay(2 * FLOW);
					for (int i = 0; i < users.size(); i++) {

						CardUser cu = (CardUser) user;
						String type = "����";
						int consumData = 2048;
						CardConsumInfo info = new CardConsumInfo(
								cu.getPhoneNumber(), type, consumData);
						infos.add(info);

					}

					break;
				} else {
					continue;
				}

			case 3:
				// �ʺ�ͻ�,ͨ��90���� �����û�:���뿨 ���˿�
				if (!(user instanceof NetworkCard)) {
					// ִ��ͨ������
					System.out.println("�ʺ�ͻ�,ͨ��90���� ");
					CallService callUser = (CallService) user;
					// ʹ�ýӿ�����,����ͨ������
					callUser.localCall(90);
					for (int i = 0; i < users.size(); i++) {
						if (user instanceof CardUser) {
							CardUser cu = (CardUser) user;
							String type = "ͨ��";
							int consumData = 90;
							CardConsumInfo info = new CardConsumInfo(
									cu.getPhoneNumber(), type, consumData);
							infos.add(info);
							break;
						}
					}
					break;
				} else {
					continue;
				}

			case 4:
				// ���뻷��ʵʩ�����ʾ����,���Ͷ���5�� �����û�:���뿨 ���˿�
				if (!(user instanceof NetworkCard)) {
					// ִ�з����ŷ���
					System.out.println("���뻷��ʵʩ�����ʾ����,���Ͷ���5��    ");
					SendService sendUser = (SendService) user;
					// ʹ�ýӿ�����,���÷����ŷ���
					sendUser.sendMessage(5);
					for (int i = 0; i < users.size(); i++) {
						if (user instanceof CardUser) {
							CardUser cu = (CardUser) user;
							String type = "����";
							int consumData = 5;
							CardConsumInfo info = new CardConsumInfo(
									cu.getPhoneNumber(), type, consumData);
							infos.add(info);
							break;
						}
					}
					break;
				} else {
					continue;
				}

			case 5:
				// ѯ����������״��,ͨ��30���� �����û�:���뿨 ���˿�
				if (!(user instanceof NetworkCard)) {
					// ִ��ͨ������
					System.out.println("ѯ����������״��,ͨ��30����   ");
					CallService callUser = (CallService) user;
					// ʹ�ýӿ�����,����ͨ������
					callUser.localCall(30);
					for (int i = 0; i < users.size(); i++) {
						if (user instanceof CardUser) {
							CardUser cu = (CardUser) user;
							String type = "ͨ��";
							int consumData = 30;
							CardConsumInfo info = new CardConsumInfo(
									cu.getPhoneNumber(), type, consumData);
							infos.add(info);
							break;
						}
					}
					break;
				} else {
					continue;
				}

			case 6:
				// ��Ů������Ƶ����,ʹ������1GB �����û�:���濨 ���˿�
				if (!(user instanceof TalkCard)) {
					// ִ����������
					System.out.println("��Ů������Ƶ����,ʹ������1GB ");
					NetService netUser = (NetService) user;
					// ʹ�ýӿ�����,������������
					netUser.netPlay((1 * FLOW));
					for (int i = 0; i < users.size(); i++) {
						if (user instanceof CardUser) {
							CardUser cu = (CardUser) user;
							String type = "����";
							int consumData = 1024;
							CardConsumInfo info = new CardConsumInfo(
									cu.getPhoneNumber(), type, consumData);
							infos.add(info);
							break;
						}
					}
					break;
				} else {
					continue;
				}

			}

			break;// ��������
		} while (true);

	}

	// ���Ѽ�¼
	public void prinConsumInfo() {
		Writer fileWriter = null;

		// Set<String> numbers = consumInfos.keySet();
		// Iterator<String> it = numbers.iterator();
		// �洢ָ���������Ѽ�¼
		
		
		try {
			fileWriter = new FileWriter(
					"C:\\Users\\Administrator\\Desktop\\���Ѽ�¼.txt");

			// ���������б����Ƿ���ڴ˿������Ѽ�¼,
			boolean isExist = false;
			// ����CardConsumInfo

			for (int i = 0; i < infos.size(); i++) {
				if (infos.size() != 0) {
					isExist = true;
				}
			}

			if (isExist) {
				StringBuffer content = new StringBuffer(
						"\t\t\t******���Ѽ�¼*******\n");
				content.append("���\t  �ֻ���\t����\t\t����(ͨ��/(����)����/(MB)/����(��))\n");
				for (int i = 0; i < infos.size(); i++) {

					CardConsumInfo info = infos.get(i);
					content.append((i + 1) + "\t" + info.getCardNumber() + "\t"
							+ info.getType() + "\t\t" + info.getConsumData()
							+ "\t \n");

				}
				System.out.println(content);
				fileWriter.write(content.toString());
				fileWriter.flush();
				System.out.println("���Ѽ�¼�Ѵ�ӡ");

			} else {
				System.out.println("�����ڴ��û������Ѽ�¼");

			}

		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}

	}

	// �ײͱ��
	public void changingPack(String name, String pwd, int num) {
		//	��������
		for (int i = 0; i < users.size(); i++) {
			User user = (User) users.get(i);
			if (user.getName().equalsIgnoreCase(name)
					&& user.getPwd().equalsIgnoreCase(pwd)) {
				switch (num) {
				case 1:
					// ���뿨
					if (user instanceof TalkCard) {
						System.out.println("�Բ���,���Ѿ��Ǹ��ײ��û��������ظ�����");
					} else if (((CardUser) user).getMoney() > ((CardUser) user)
							.getAccount()) {
						String u1 = " ";
						String u2 = " ";
						String u3 = " ";
						double u4 = 0;
						//����ǿ��ת������,�����Ӹ����е�����
						u4 = ((CardUser) user).getMoney();
						u1 = ((CardUser) user).getName();
						u2 = ((CardUser) user).getPwd();
						u3 = ((CardUser) user).getPhoneNumber();
						User tmep = new TalkCard(u1, u2, u3,
								TalkCardValue.ACCOUNT.getValue(), u4,
								TalkCardValue.TALKTIME.getValue(),
								TalkCardValue.SMSCOUNT.getValue());
						users.add(tmep);
						users.remove(i);
						System.out.println("����ɹ�");
						break;
					} else {
						System.out.println("�˻����㣬���ֵ��ʹ��");
					}
					break;
				case 2:
					// ���濨
					if (user instanceof NetworkCard) {
						System.out.println("�Բ���,���Ѿ��Ǹ��ײ��û��������ظ�����");
					} else if (((CardUser) user).getMoney() > ((CardUser) user)
							.getAccount()) {
						String u1 = " ";
						String u2 = " ";
						String u3 = " ";
						double u4 = 0;
						u4 = ((CardUser) user).getMoney();
						u1 = ((CardUser) user).getName();
						u2 = ((CardUser) user).getPwd();
						u3 = ((CardUser) user).getPhoneNumber();
						User tmep = new NetworkCard(u1, u2, u3,
								NetworkCardValue.ACCOUNT.getValue(), u4,
								NetworkCardValue.FLOW.getValue());
						users.add(tmep);
						users.remove(i);
						System.out.println("����ɹ�");
						break;
					} else {
						System.out.println("�˻����㣬���ֵ��ʹ��");
					}
					break;
				case 3:
					// ���˿�
					if (user instanceof SuperCard) {
						System.out.println("�Բ���,���Ѿ��Ǹ��ײ��û��������ظ�����");
					} else if (((CardUser) user).getMoney() > ((CardUser) user)
							.getAccount()) {
						String u1 = " ";
						String u2 = " ";
						String u3 = " ";
						double u4 = 0;
						u4 = ((CardUser) user).getMoney();
						u1 = ((CardUser) user).getName();
						u2 = ((CardUser) user).getPwd();
						u3 = ((CardUser) user).getPhoneNumber();
						User tmep = new SuperCard(u1, u2, u3,
								SuperCardValue.ACCOUNT.getValue(), u4,
								SuperCardValue.TALKTIME.getValue(),
								SuperCardValue.SMSCOUNT.getValue(),
								SuperCardValue.FLOW.getValue());
						users.add(tmep);
						users.remove(i);
						System.out.println("����ɹ�");
						break;
					} else {
						System.out.println("�˻����㣬���ֵ��ʹ��");
					}
					break;

				default:

					break;
				}
			}
		}

	}
}
