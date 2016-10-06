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
		
		// �ӥ��x���b���K�X(�o�ӽd�ҬOgoogle��)
		// �ϥΫe�n���Ngoogle�b���̭����]�w:[���\�w���ʸ��C�����ε{��] �]�w�B�󰱥Ϊ��A => �}��
		final String account = "[your_account]";
		final String password = "[your_password]";
		
		// google��mail host and port(�Ӹ�T�����})
		String mailHost = "smtp.gmail.com";
		String mailPort = "465";
		
		// yahoo��mail host and port(�Ӹ�T�����})
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
			message.setSubject("�{�����ͪ��H��"); //�l����D
			message.setText("�ѵ{���۰ʲ��͡A�ФŦ^�H!!"); //�l�󤺮e

			Transport.send(message);

			log.debug("�H��w���\�H�X");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}