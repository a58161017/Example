package kiuno.example.email;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class SendMailTLS {
	private static Logger log = null;
	public static void main(String[] args) {
		log = (new MyLogger(SendMailTLS.class)).getLogger();
		String mailer = "[who_SendMail]";
		String recipient = "[who_ReceiveMail]";
		
		// 該平台的帳號密碼(這個範例是google的)
		// 使用前要先將google帳號裡面的設定:[允許安全性較低的應用程式] 設定處於停用狀態 => 開啟
		final String account = "[your_account]";
		final String password = "[your_password]";
		
		// google的mail host and port(該資訊有公開)
		String mailHost = "smtp.gmail.com";
		String mailPort = "587";
		
		// yahoo的mail host and port(該資訊有公開)
		// String mailHost = "smtp.mail.yahoo.com";
		// String mailPort = "587";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", mailHost);
		props.put("mail.smtp.port", mailPort);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(account, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailer));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject("程式產生的信件");
			message.setText("測試信件");

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("由程式自動產生，請勿回信!!"); //會把message.setText("測試信件");這行的內容覆蓋掉
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			String filename = "file.txt"; //要附加的檔案
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			Transport.send(message);

			log.debug("信件已成功寄出");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}