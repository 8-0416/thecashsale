package com.cashsale.util;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 发送激活邮件
 * @author Sylvia
 * 2018年10月16日
 */
public class MailUtil {
	
	public static void sendMail(String to, String code, String password, String nickname) throws Exception
	{
		
		//创建连接对象，连接到邮箱服务器
		Properties props = new Properties();
		//"host"为主机名，value：服务器地址（因为是本地发送，所以可省略）
//		props.setProperty("host", value);
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("Cashsale0416@cashsale.com", "cashsale0416");
			}
		});
		
		//创建邮件对象
		Message message = new MimeMessage(session);
		
		//设置发件人
		message.setFrom(new InternetAddress("Cashsale0416@cashsale.com"));
		
		//设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		
		//设置邮件主题
		message.setSubject("来自“现卖”的激活邮件");
		
		//设置邮件的正文
		message.setContent("<h1>亲爱的"+nickname+"：</h1><br><h3>欢迎加入现卖！<br> "
				+ "请点击下面的连接完成注册：<br></h3><a href='http://localhost:8080/thecashsale/active?"
				+ "code="+code+"&password="+password+"'>http://localhost:8080/thecashsale/active?code="+code+"</a>"
						+ "<br><h5>如以上连接无法点击，请将其复制到浏览器中打开（请于24小时内，完成验证，逾期需重新注册）",
				"text/html;charset=utf-8");
		
		//发送一封激活邮件
		Transport.send(message);
		//System.out.println(message.getContent());
	}
}
