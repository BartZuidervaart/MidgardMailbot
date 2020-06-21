package com.midgard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;

public class ScreenMail extends TimerTask {

	MailProxyAddress bart = new MailProxyAddress("Bart", "Russia", "Zuidervaart.bart@gmail.com");

	MailProxyAddress[] addresses = new MailProxyAddress[] { bart };

	@Override
	public void run() {
		System.out.println("starting...");
		JavaMailUtil.setMyAccountEmail("midgard.diplomacy@gmail.com");
		JavaMailUtil.setPassword("890IOPkl;midgard");
		Message[] messages = JavaMailUtil.receiveMail();
		messages = filterOutMessages(messages, Flags.Flag.DELETED);
		messages = filterOutMessages(messages, Flags.Flag.SEEN);
		confirmationReply(messages);
		printMessages(messages);
		// JavaMailUtil.sendMail("midgard.diplomacy@gmail.com", "MailBot", "You son of a
		// bitch. I'm in.");
		// JavaMailUtil.closingConnections();
		System.out.println("...done.");
	}

	private Message[] filterOutMessages(Message[] messages, Flag flag) {
		ArrayList<Message> foundMessages = new ArrayList<Message>();
		try {
			for (int i = 0; i < messages.length; i++) {

				if (!messages[i].getFlags().contains(flag)) {
					foundMessages.add(messages[i]);
				}

			}
		} catch (MessagingException ex) {

		}
		return foundMessages.toArray(new Message[foundMessages.size()]);
	}

	private void confirmationReply(Message[] messages) {
		try {
			for (int i = 0; i < messages.length; i++) {
				Address[] from = messages[i].getFrom();
				String replyMessage = "";
				if (!messages[i].getContentType().toString().split(" ")[0].equals("TEXT/HTML;")
						&& !messages[i].getContentType().toString().split(" ")[0].equals("TEXT/PLAIN;")) {
					replyMessage = "Could not read message because it was not TEXT/HTML or TEXT/PLAIN.\nTry finding a \"plain text\" option for your mail service. \nFound instead:"
							+ messages[i].getContent();
				} else {

					replyMessage = filterMessage(messages[i]);// "Received message:\n"+messages[i].getContent();
				}
				for (int j = 0; j < from.length; j++) {
					JavaMailUtil.sendMail(from[j].toString(), "MailBot autoreply", replyMessage);
				}
			}
		} catch (NoSuchProviderException nspe) {
			nspe.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String filterMessage(Message message) {
		try {
			Address[] from = message.getFrom();
			for (int j = 0; j < from.length; j++) {
				for (int i = 0; i < addresses.length; i++) {
					if(from[j].toString().equals(addresses[i].getAddress())) {
						
					}
				}
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void printMessages(Message[] messages) {

		try {
			System.out.println("You have " + messages.length + " unseen messages");
			for (int i = 0; i < messages.length; i++) {
				System.out.println("Email Number: " + (i + 1));
				System.out.println("Subject: " + messages[i].getSubject());
				System.out.println("Subject: " + messages[i].getFrom()[0]);
				System.out.println("Send date: " + messages[i].getSentDate());
				System.out.println("Flags: " + messages[i].getFlags());
				System.out.println("Message: " + messages[i].getContentType());
				messages[i].setFlag(Flag.SEEN, true);
			}
		} catch (NoSuchProviderException nspe) {
			nspe.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		} // catch (IOException e) {
			// e.printStackTrace();
			// }

	}

}
