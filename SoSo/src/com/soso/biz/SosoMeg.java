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
 * 嗖嗖业务类
 * 
 * @author Administrator
 * 
 */
public class SosoMeg {
	Scanner input = new Scanner(System.in);// 定义在类级,方便全局使用
	UserUtils utils = new UserUtils();
	public static void main(String[] args) {
		SosoMeg soso = new SosoMeg();
		soso.start();
	}

	/**
	 * 主流程(主框架)
	 */
	public void start() {
		int menuChoose = 0;
		int typeChoose = 0;
		int UserChoose = 0;
		//数据初始话--载入用户
		utils.inti();
		// 使用分支语句,根据选择编号执行相应功能
		do {
			System.out.println("***********欢迎使用嗖嗖移动大厅***************");
			System.out.println("1:注册新用户     2:登录嗖嗖     3:资费说明     4:退出系统");
			System.out.print("请选择:");
			menuChoose = input.nextInt();
			switch (menuChoose) {
			case 1:
				User newUser = null;// 创建User的类型为空,存储生成的用户
				int brandChoose = 0;
				do{
				// 注册新用户
				System.out.println("*****注册新用户******");
				System.out.print("嗖嗖移动用户\n");
					// 执行创建嗖嗖移动用户
					System.out.print("1:话唠卡   2:网虫卡   3:超人卡\n请选择套餐");
					 brandChoose = input.nextInt();
					// 调用注册用户方法
				}while(brandChoose > 3);
					newUser = registCardUser(brandChoose);
					utils.addUserToUserList(newUser);
					System.out.println("********注册成功*********");
					newUser.showInfo();
				break;
			case 2:
				boolean bool = false;//标志是否退出登录嗖嗖菜单
				do {
					// 登录搜搜
					
					// 输入用户名与密码,判断是哪种用户
					System.out.print("请输入用户名:");
					String name = input.next();
					System.out.print("请输入密码:");
					String pwd = input.next();
					//获取用户类型
					typeChoose = utils.getTypeUser(name, pwd);					//
					if (typeChoose == 1) {
						int b = 0;
						do {
							System.out.println("*****管理员菜单*****");
							System.out.print("1:查询用户信息   2:办理用户退网\n请选择:");
							typeChoose = input.nextInt();
							if (typeChoose == 1) {
								// 查询用户信息
								System.out.println("******查询用户信息*******");
								utils.showUsers();
								bool = true;
							
							} else if (typeChoose == 2) {
								// 办理用户退网
								System.out.println("******办理用户退网*******");
								System.out.print("请输入手机号");
								String phoneNumber = input.next();
								utils.removeUser(phoneNumber);
							}  else if (typeChoose > 2){							
								bool = false;
							} 
						} while (b < 2);
						break;//退出登录嗖嗖菜单
					} else if (typeChoose == 2) {
						do {
							System.out.println("*****嗖嗖用户菜单*****");
							System.out.println("1:本月消费记录");
							System.out.println("2:套餐余量查询");
							System.out.println("3:话费充值");
							System.out.println("4:使用嗖嗖");
							System.out.println("5:更改套餐");
							System.out.println("6:消费记录");
							System.out.print("请选择:");
							UserChoose = input.nextInt();
							switch (UserChoose) {
							case 1:
								// 本月账单
								System.out.println("*******本月账单查询******");
							
								utils.showAccountDetail(name, pwd);
								break;
							case 2:
								// 套餐余量
								System.out.println("******套餐余量查询******");
							
								utils.showRemainDetail(name, pwd);
								break;
							case 3:
								// 话费充值
								System.out.println("********充值话费*********");
								System.out.println("请输入手机号");
								String phoneNumber = input.next();
								System.out.println("请输入充值金额"); 	
								double rechargeMoney = input.nextDouble();
								utils.rechargeMoney(phoneNumber, rechargeMoney);
								break;
							case 4:
								// 使用嗖嗖
								System.out.println("******使用嗖嗖*******");
								try {
									utils.useSOSO(name,pwd);
								} catch (Exception e) {
									
									e.printStackTrace();
									continue;
								}
								break;
							case 5:
								System.out.print("1:话唠卡   2:网虫卡   3:超人卡\n请选择套餐");
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
						break;//退出嗖嗖用户菜单菜单
					} else if(typeChoose == 0) {
						System.out.println("无此用户");
						return;
					} else {
						bool = true;
						
					}
				} while (bool);
				break;//退出登录嗖嗖菜单
			case 3:
				// 资费说明
				utils.showDescription();
				break;
			case 4:
				// 退出系统
				System.out.println("退出系统");
				break;
			default:
				// 返回主菜单
				break;
			}
		} while (menuChoose != 4);
	}

	/**
	 * 注册管理员用户
	 */
	public User registAdminUser() {
		System.out.print("请输入用户名：");
		String name = input.next();
		System.out.print("请输入密码：");
		String pwd = input.next();
		User adminUser = new AdminUser(name, pwd);
		return adminUser;
	}

	/**
	 * 注册嗖嗖移动用户 参数 品牌套餐编号 返回值 嗖嗖移动对象
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

		// 提供九个可选择的号码
		String[] numbers = utils.getPhoneNumber(9);
		System.out.println("\t\t     ******可选择的卡号********");
		for (int i = 0; i < numbers.length; i++) {
			System.out.print((i + 1) + "." + numbers[i] + "\t\t");
			if ((i + 1) % 3 == 0) {
				System.out.println();
			}
		}
		System.out.print("请选择卡号编号(1~9)");
		String phoneNumber = numbers[input.nextInt() - 1];
		System.out.print("请输入用户名：");
		String name = input.next();
		System.out.print("请输入密码：");
		String pwd = input.next();
		boolean bool = false;
		do {
			System.out.println("请输入预存的金额");
			double money = input.nextDouble();
			// 通过用户品牌编号生成嗖嗖移动用户对象
			switch (type) {
			case 1:
				cardUser = new TalkCard(name, pwd, phoneNumber,
						TalkCardValue.ACCOUNT.getValue(), money
								- TalkCardValue.ACCOUNT.getValue(),
						TalkCardValue.TALKTIME.getValue(),
						TalkCardValue.SMSCOUNT.getValue());

				break;
			case 2:
				// 网虫卡
				cardUser = new NetworkCard(name, pwd, phoneNumber,
						NetworkCardValue.ACCOUNT.getValue(), money
								- NetworkCardValue.ACCOUNT.getValue(),
						NetworkCardValue.FLOW.getValue());
				break;
			case 3:
				// 超人卡
				cardUser = new SuperCard(name, pwd, phoneNumber,
						SuperCardValue.ACCOUNT.getValue(), money
								- SuperCardValue.ACCOUNT.getValue(),
						SuperCardValue.TALKTIME.getValue(),
						SuperCardValue.SMSCOUNT.getValue(),
						SuperCardValue.FLOW.getValue());
				break;
			}
			if (money < cardUser.getAccount()) {
				System.out.println("您的预存款不足已支付本月固定套餐资费,请重新充值");
				bool = true;
			} else {
				bool = false;
			}
		} while (bool);

		return cardUser;

	}

}
