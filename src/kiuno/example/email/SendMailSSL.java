package kiuno.example.email;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class SendMailSSL {
	private static Logger log = null;
	public static void main(String[] args) {
		log = (new MyLogger(SendMailSSL.class)).getLogger();
		String mailer = "[who_SendMail]";
		String recipient = "[who_ReceiveMail]";
		
		// 該平台的帳號密碼(這個範例是google的)
		// 使用前要先將google帳號裡面的設定:[允許安全性較低的應用程式] 設定處於停用狀態 => 開啟
		final String account = "[your_account]";
		final String password = "[your_password]";
		
		// google的mail host and port(該資訊有公開)
		String mailHost = "smtp.gmail.com";
		String mailPort = "465";
		
		// yahoo的mail host and port(該資訊有公開)
		// String mailHost = "smtp.mail.yahoo.com";
		// String mailPort = "465";

		Properties props = new Properties();
		props.put("mail.smtp.host", mailHost);
		props.put("mail.smtp.socketFactory.port", mailPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", mailPort);

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(account, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailer));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject("程式產生的信件"); //郵件標題
			message.setText("由程式自動產生，請勿回信!!"); //郵件內容

			Transport.send(message);

			log.debug("信件已成功寄出");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}