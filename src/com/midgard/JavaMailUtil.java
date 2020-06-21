package com.midgard;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class JavaMailUtil {

	private static String myAccountEmail;
	private static String password;

	public JavaMailUtil(String userName, String password) {
		JavaMailUtil.myAccountEmail = userName;
		JavaMailUtil.password = password;
	}

	public static Message[] receiveMail(String userName, String password) {
		try {
			Properties properties = new Properties();
			properties.setProperty("mail.store.protocol", "imaps");

			Session emailSession = Session.getDefaultInstance(properties);
			Store emailStore = emailSession.getStore("imaps");

			emailStore.connect("imap.gmail.com", userName, password);

			Folder emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);

			Message messages[] = emailFolder.getMessages();

			// closing emailFolder and emailStore
			//emailFolder.close(false);
			//emailStore.close();

			return messages;

		} catch (NoSuchProviderException nspe) {
			nspe.printStackTrace();
			return null;
		} catch (MessagingException me) {
			me.printStackTrace();
			return null;
		}

	}

	public static void closingConnections() {
		try {
			Properties properties = new Properties();
			properties.setProperty("mail.store.protocol", "imaps");

			Session emailSession = Session.getDefaultInstance(properties);
			Store emailStore = emailSession.getStore("imaps");

			Folder emailFolder = emailStore.getFolder("INBOX"); //throws an connection error... already disconnected?

			// closing emailFolder and emailStore
			emailFolder.close(false);
			emailStore.close();
		} catch (NoSuchProviderException nspe) {
			nspe.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		}

	}

	public static Message[] receiveMail() {
		return receiveMail(myAccountEmail, password);
	}

	public static void sendMail(String recepient, String subject, String content) throws Exception {
		System.out.println("Preparing to send email...");
		Properties properties = new Properties();

		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});

		Message message = prepareMessage(session, myAccountEmail, recepient, subject, content);

		Transport.send(message);
		System.out.println("Message send successfully");
	}

	private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String subject,
			String content) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject(subject);
			message.setContent(content, "text/html");
			return message;
		} catch (Exception ex) {
			Logger.getLogger(JavaMailUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	// mail.smtp.auth;
	// mail.smtp.starttls.enable
	// mail.smtp.host
	// mail.smtp.port

	public static String getMyAccountEmail() {
		return myAccountEmail;
	}

	public static void setMyAccountEmail(String myAccountEmail) {
		JavaMailUtil.myAccountEmail = myAccountEmail;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		JavaMailUtil.password = password;
	}

}
