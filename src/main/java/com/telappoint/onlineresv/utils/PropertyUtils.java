package com.telappoint.onlineresv.utils;

import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyUtils {
	
	private static final Log logger = LogFactory.getLog(PropertyUtils.class);
	
	private static Properties getMailSettings(Properties mailProperties) {
		logger.debug("getMailSettings method entered.");
		String debug = mailProperties.getProperty("mail.smtp.debug");
		String userName = mailProperties.getProperty("mail.smtp.user");
		String port = mailProperties.getProperty("mail.smtp.port");
		String starttls = mailProperties.getProperty("mail.smtp.starttls.enable");
		String auth = mailProperties.getProperty("mail.smtp.auth");
		String socketFactoryClass = "javax.net.ssl.SSLSocketFactory";
		String fallback = "false";

		Properties props = new Properties();
		props.put("mail.smtp.user", userName);
		props.put("mail.smtp.host", "localhost");

		if (!"".equals(port)) {
			props.put("mail.smtp.port", port);
		}
		if (!"".equals(starttls)) {
			props.put("mail.smtp.starttls.enable", starttls);
		}
		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.debug", debug);

		if (!"".equals(port)) {
			props.put("mail.smtp.socketFactory.port", port);
		}
		props.put("mail.smtp.socketFactory.class", socketFactoryClass);
		props.put("mail.smtp.socketFactory.fallback", fallback);
		logger.debug("getMailSettings method exit.");
		return props;
	}
	
	public static void sendErrorEmail(String subject,String body) {
		logger.debug("sendEmail method entered.");
		Properties mailProperties = FileUtils.getMailProperties();
		String sendMail = mailProperties.getProperty("error.mail.send");

		if (sendMail != null && "yes".equalsIgnoreCase(sendMail)) {
			String userName = mailProperties.getProperty("mail.smtp.user");
			String passWord = mailProperties.getProperty("mail.smtp.password");
			String host = mailProperties.getProperty("mail.smtp.hostname");

			String fromAddress = mailProperties.getProperty("mail.fromaddress");
			String toAddress = mailProperties.getProperty("error.mail.to");
			String ccAddress = mailProperties.getProperty("error.mail.cc");
			String replyAddress = mailProperties.getProperty("mail.replayaddress");

			Properties props = getMailSettings(mailProperties);

			try {
				Session session = Session.getDefaultInstance(props, null);
				MimeMessage msg = new MimeMessage(session);
				msg.setSubject(subject);
				msg.setFrom(new InternetAddress(fromAddress));
				InternetAddress[] replyAddresses = new InternetAddress[1];
				replyAddresses[0] = new InternetAddress(replyAddress);
				msg.setReplyTo(replyAddresses);

				// This part for CC Address Details
				if (ccAddress != null && !"".equals(ccAddress) && ccAddress.length() > 0) {
					InternetAddress[] inetAddress = getInetAddress(ccAddress);
					msg.setRecipients(Message.RecipientType.CC, inetAddress);
				}
				// This part for TO Address Details
				if (toAddress != null && !"".equals(toAddress) && toAddress.length() > 0) {
					InternetAddress[] inetAddress = getInetAddress(toAddress);
					msg.setRecipients(Message.RecipientType.TO, inetAddress);
				}

				msg.setContent(body, "text/html");
				msg.saveChanges();
				Transport transport = session.getTransport("smtp");
				transport.connect(host, userName, passWord);
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();
			} catch (AddressException ade) {
				logger.error("Error:" + ade, ade);
			} catch (MessagingException me) {
				logger.error("Error:" + me, me);
			}
		}
	}
	
	public static InternetAddress[] getInetAddress(String address) throws AddressException {
		InternetAddress[] inetAddress = null;
		StringTokenizer tokens = new StringTokenizer(address, ",");
		if (tokens != null && tokens.hasMoreTokens()) {
			inetAddress = new InternetAddress[tokens.countTokens()];
			int i = 0;
			while (tokens.hasMoreTokens()) {
				String token = tokens.nextToken();
				inetAddress[i++] = new InternetAddress(token.trim());
			}
		}
		return inetAddress;
	}
}
