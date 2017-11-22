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
 * 工具类(操作类)
 * 
 * @author Administrator
 * 
 */
public class UserUtils {
	
	Scanner input = new Scanner(System.in);
	// 创建用户列表:(管理员和嗖嗖用户)
	List<User> users = new ArrayList<User>();
	// 创建消费记录列表
	Map<String, List<CardConsumInfo>> consumInfos = new HashMap<String, List<CardConsumInfo>>();
	List<CardConsumInfo> infos = new ArrayList<CardConsumInfo>();
	final int FLOW = 1024;// 定义常量,进行流量的数据转换
	// 格式化器---取一位小数点
	DecimalFormat format = new DecimalFormat("#.0");

	// 初始化数据
	public void inti() {
		// 创建一个管理员
		User u1 = new AdminUser("admin", "123");
		// 创建一个话唠卡用户
		User u2 = new TalkCard("TalkCard", "123", "177-8888-9999",
				TalkCardValue.ACCOUNT.getValue(), 200,
				TalkCardValue.TALKTIME.getValue(),
				TalkCardValue.SMSCOUNT.getValue());
		// 创建一个网虫卡用户
		User u3 = new NetworkCard("NetCard", "12345", "188-0000-9999",
				NetworkCardValue.ACCOUNT.getValue(), 300,
				NetworkCardValue.FLOW.getValue());
		// 创建一个超人卡用户
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

	// 创建卡号(已139开头,11位数)
	public String createNumber() {
		Random random = new Random();
		boolean isExist = false;// 记录现有用户是否存在此卡号
		int temp = 0;// 存储八位随机数
		String phoneNumber = "";// 存储生成的卡号
		do {
			do {
				// 生成八位随机数
				temp = random.nextInt(99999999);
				// 不能小于10000000
			} while (temp < 10000000);
			// 生成之前,前面加上139
			phoneNumber = "139" + temp;
			// 追加一个字符(-)
			StringBuffer sb = new StringBuffer(phoneNumber);
			for (int j = sb.length() - 4; j > 0; j -= 4) {
				sb.insert(j, '-');
				phoneNumber = sb.toString();
			}
			// 和现有用户的卡号作比较,不能重复
			for (User user : users) {
				// 如果是一个嗖嗖用户,就进行比较卡号
				if (user instanceof CardUser) {
					// 进行强制转换
					CardUser cardUser = (CardUser) user;
					if (cardUser.getPhoneNumber().equals(phoneNumber)) {
						isExist = true;
					}
				}
			}
		} while (isExist);
		return phoneNumber;
	}

	// 生成指定个数的卡号列表
	public String[] getPhoneNumber(int count) {
		String[] numbers = new String[count];
		for (int i = 0; i < count; i++) {
			numbers[i] = createNumber();

		}
		return numbers;
	}

	// 向集合中添加用户
	public void addUserToUserList(User user) {
		users.add(user);
	}

	// 判定用户与管理员的类型
	public int getTypeUser(String name, String pwd) {
		int type = 0;// 0:无此用户，1:管理员用户，2:嗖嗖用户
		// 遍历集合
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);// 取出集合中的数据
			// 忽略大写进行判断
			if (user.getName().equalsIgnoreCase(name)
					&& user.getPwd().equalsIgnoreCase(pwd)) {
				// 判断用户类型
				if (user instanceof AdminUser) {
					type = 1;
				} else {
					type = 2;
				}

			}
		}
		return type;
	}

	// 资费说明
	public void showDescription() {
		// I/O读写资费说明
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("C:\\Users\\Administrator\\Desktop\\资费说明.txt");
			br = new BufferedReader(fr);
			// 读取数据
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

	// 管路员功能--查询所有用户信息
	public void showUsers() {
		StringBuffer adminStr = new StringBuffer("******管理员用户******\n");// 存储所有管理员信息
		StringBuffer cardStr = new StringBuffer("******嗖嗖用户********\n");// 存储所有用户信息
		StringBuffer temp = new StringBuffer();// 临时存储一条信息信息
		int adminCount = 0;// 管理员计数器
		int cardCount = 0;// 用户计数器
		// 遍历集合
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			// 清空字符串缓冲区
			temp.delete(0, temp.length());
			User user = it.next();
			// 存储信息
			temp.append(user.getName() + "\t" + user.getPwd() + "\t");
			// 进行类型判断
			if (user instanceof AdminUser) {
				adminCount++;
				adminStr.append(adminCount + ".管理员\n" + temp + "\n");
			} else if (user instanceof TalkCard) {
				// 话唠卡
				cardCount++;
				temp.append("手机号:" + ((TalkCard) user).getPhoneNumber() + "\t");
				temp.append("通话时长:" + ((TalkCard) user).getTalkTime() + "分钟\t");
				temp.append("短信条数:" + ((TalkCard) user).getSmsCount() + "条\t");
				temp.append("月资费:" + ((TalkCard) user).getAccount() + "元\t");
				temp.append("账户余额:" + ((TalkCard) user).getMoney() + "元\t\n");
				cardStr.append(cardCount + ":\t话唠卡:" + temp);
			} else if (user instanceof NetworkCard) {
				// 网虫卡
				cardCount++;
				temp.append("手机号:" + ((NetworkCard) user).getPhoneNumber()
						+ "\t");
				temp.append("上网流量:"
						+ format.format(((NetworkCard) user).getFlow() * 1.0
								/ FLOW) + "GB\t");
				temp.append("月资费:" + ((NetworkCard) user).getAccount() + "元\t");
				temp.append("账户余额:" + ((NetworkCard) user).getMoney() + "元\t\n");
				cardStr.append(cardCount + ":\t网虫卡:" + temp);

			} else if (user instanceof SuperCard) {
				// 超人卡
				cardCount++;
				temp.append("手机号:" + ((SuperCard) user).getPhoneNumber() + "\t");
				temp.append("通话时长:" + ((SuperCard) user).getTalkTime() + "分钟\t");
				temp.append("短信条数:" + ((SuperCard) user).getSmsCount() + "条\t");
				temp.append("上网流量:"
						+ format.format(((SuperCard) user).getFlow() * 1.0
								/ FLOW) + "GB\t");
				temp.append("月资费:" + ((SuperCard) user).getAccount() + "元\t");
				temp.append("账户余额:" + ((SuperCard) user).getMoney() + "元\t\n");
				cardStr.append(cardCount + ":\t超人卡:" + temp);
			}
		}
		System.out.println(adminStr);
		System.out.println(cardStr);

	}

	// 办理退网
	public void removeUser(String phoneNumber) {
		boolean isdel = false;// 记录是删除
		// 遍历集合
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User user = it.next();
			if (user instanceof CardUser) {
				CardUser cardUser = (CardUser) user;
				if (cardUser.getPhoneNumber().equals(phoneNumber)) {
					// 找到删除元素
					it.remove();
					isdel = true;
					System.out.println("手机号为" + phoneNumber + "的用户办理退网成功");
					break;
				}

			}
		}
		if (!isdel) {
			System.out.println("没有找到此用户");
		}
	}

	// 用户本月账单查询
	public void showAccountDetail(String name, String pwd) {
		for (int i = 0; i < users.size(); i++) {
			// 如果是嗖嗖移动用户,并且用户名与密码相等
			if (users.get(i) instanceof CardUser
					&& users.get(i).getName().equalsIgnoreCase(name)
					&& users.get(i).getPwd().equalsIgnoreCase(pwd)) {
				CardUser cardUser = (CardUser) users.get(i);
				System.out.println(cardUser.getAccountDetail());

			}

		}
	}

	// 用户余量查询
	public void showRemainDetail(String name, String pwd) {
		for (int i = 0; i < users.size(); i++) {
			// 如果是嗖嗖移动用户,并且用户名与密码相等
			if (users.get(i) instanceof CardUser
					&& users.get(i).getName().equalsIgnoreCase(name)
					&& users.get(i).getPwd().equalsIgnoreCase(pwd)) {
				CardUser cardUser = (CardUser) users.get(i);
				System.out.println(cardUser.getRemainDetail());

			}

		}
	}

	// 话费充值
	public void rechargeMoney(String phoneNumber, double rechargeMoney) {
		boolean isFind = false;
		// 从集合中查找传入进来的用户
		for (int i = 0; i < users.size(); i++) {

			if (users.get(i) instanceof CardUser) {
				CardUser cardUser = (CardUser) users.get(i);
				if (cardUser.getPhoneNumber().equals(phoneNumber)) {
					isFind = true;
					cardUser.recharge(rechargeMoney);
					System.out.println("充值成功:卡号为" + cardUser.getPhoneNumber()
							+ "\t当前金额为" + cardUser.getMoney() + "元");
					break;
				}

			}
		}
		if (!isFind) {
			System.out.println("无此用户");
		}

	}

	// 根据用户名-密码 获得一个相符的嗖嗖用户,并返回
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

	// 使用嗖嗖
	public void useSOSO(String name, String pwd) throws Exception {
		User user = getUser(name, pwd);
		// 生成1~6之间的随机数
		Random random = new Random();
		int number = 0;
		do {
			number = random.nextInt(6) + 1;
			switch (number) {
			case 1:
				// 查看微信朋友圈,使用流量30MB 适用用户:网虫卡 超人卡
				if (!(user instanceof TalkCard)) {
					// 执行上网方法
					System.out.println("查看微信朋友圈,使用流量30MB ");
					NetService netUser = (NetService) user;
					// 使用接口引用,调用上网方法
					netUser.netPlay(30);
					for (int i = 0; i < users.size(); i++) {

						CardUser cu = (CardUser) user;
						String type = "上网";
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
				// 晚上手机在线看韩剧,不留神睡着了,使用流量2GB 适用用户:网虫卡 超人卡
				if (!(user instanceof TalkCard)) {
					// 执行上网方法
					System.out.println("晚上手机在线看韩剧,不留神睡着了,使用流量2GB ");
					NetService netUser = (NetService) user;
					// 使用接口引用,调用上网方法
					netUser.netPlay(2 * FLOW);
					for (int i = 0; i < users.size(); i++) {

						CardUser cu = (CardUser) user;
						String type = "上网";
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
				// 问候客户,通话90分钟 适用用户:话唠卡 超人卡
				if (!(user instanceof NetworkCard)) {
					// 执行通话方法
					System.out.println("问候客户,通话90分钟 ");
					CallService callUser = (CallService) user;
					// 使用接口引用,调用通话方法
					callUser.localCall(90);
					for (int i = 0; i < users.size(); i++) {
						if (user instanceof CardUser) {
							CardUser cu = (CardUser) user;
							String type = "通话";
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
				// 参与环境实施方案问卷调查,发送短信5条 适用用户:话唠卡 超人卡
				if (!(user instanceof NetworkCard)) {
					// 执行发短信方法
					System.out.println("参与环境实施方案问卷调查,发送短信5条    ");
					SendService sendUser = (SendService) user;
					// 使用接口引用,调用发短信方法
					sendUser.sendMessage(5);
					for (int i = 0; i < users.size(); i++) {
						if (user instanceof CardUser) {
							CardUser cu = (CardUser) user;
							String type = "短信";
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
				// 询问妈妈身体状况,通话30分钟 适用用户:话唠卡 超人卡
				if (!(user instanceof NetworkCard)) {
					// 执行通话方法
					System.out.println("询问妈妈身体状况,通话30分钟   ");
					CallService callUser = (CallService) user;
					// 使用接口引用,调用通话方法
					callUser.localCall(30);
					for (int i = 0; i < users.size(); i++) {
						if (user instanceof CardUser) {
							CardUser cu = (CardUser) user;
							String type = "通话";
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
				// 和女朋友视频聊天,使用流量1GB 适用用户:网虫卡 超人卡
				if (!(user instanceof TalkCard)) {
					// 执行上网方法
					System.out.println("和女朋友视频聊天,使用流量1GB ");
					NetService netUser = (NetService) user;
					// 使用接口引用,调用上网方法
					netUser.netPlay((1 * FLOW));
					for (int i = 0; i < users.size(); i++) {
						if (user instanceof CardUser) {
							CardUser cu = (CardUser) user;
							String type = "上网";
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

			break;// 跳出场景
		} while (true);

	}

	// 消费记录
	public void prinConsumInfo() {
		Writer fileWriter = null;

		// Set<String> numbers = consumInfos.keySet();
		// Iterator<String> it = numbers.iterator();
		// 存储指定卡的消费记录
		
		
		try {
			fileWriter = new FileWriter(
					"C:\\Users\\Administrator\\Desktop\\消费记录.txt");

			// 现有消费列表中是否存在此卡号消费记录,
			boolean isExist = false;
			// 遍历CardConsumInfo

			for (int i = 0; i < infos.size(); i++) {
				if (infos.size() != 0) {
					isExist = true;
				}
			}

			if (isExist) {
				StringBuffer content = new StringBuffer(
						"\t\t\t******消费记录*******\n");
				content.append("序号\t  手机号\t类型\t\t数据(通话/(分钟)上网/(MB)/短信(条))\n");
				for (int i = 0; i < infos.size(); i++) {

					CardConsumInfo info = infos.get(i);
					content.append((i + 1) + "\t" + info.getCardNumber() + "\t"
							+ info.getType() + "\t\t" + info.getConsumData()
							+ "\t \n");

				}
				System.out.println(content);
				fileWriter.write(content.toString());
				fileWriter.flush();
				System.out.println("消费记录已打印");

			} else {
				System.out.println("不存在此用户的消费记录");

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

	// 套餐变更
	public void changingPack(String name, String pwd, int num) {
		//	遍历集合
		for (int i = 0; i < users.size(); i++) {
			User user = (User) users.get(i);
			if (user.getName().equalsIgnoreCase(name)
					&& user.getPwd().equalsIgnoreCase(pwd)) {
				switch (num) {
				case 1:
					// 话唠卡
					if (user instanceof TalkCard) {
						System.out.println("对不起,你已经是该套餐用户，无需重复办理");
					} else if (((CardUser) user).getMoney() > ((CardUser) user)
							.getAccount()) {
						String u1 = " ";
						String u2 = " ";
						String u3 = " ";
						double u4 = 0;
						//父类强制转换子类,访问子父类中的属性
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
						System.out.println("办理成功");
						break;
					} else {
						System.out.println("账户余额不足，请充值后使用");
					}
					break;
				case 2:
					// 网虫卡
					if (user instanceof NetworkCard) {
						System.out.println("对不起,你已经是该套餐用户，无需重复办理");
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
						System.out.println("办理成功");
						break;
					} else {
						System.out.println("账户余额不足，请充值后使用");
					}
					break;
				case 3:
					// 超人卡
					if (user instanceof SuperCard) {
						System.out.println("对不起,你已经是该套餐用户，无需重复办理");
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
						System.out.println("办理成功");
						break;
					} else {
						System.out.println("账户余额不足，请充值后使用");
					}
					break;

				default:

					break;
				}
			}
		}

	}
}
