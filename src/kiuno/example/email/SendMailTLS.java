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
		
		// �ӥ��x���b���K�X(�o�ӽd�ҬOgoogle��)
		// �ϥΫe�n���Ngoogle�b���̭����]�w:[���\�w���ʸ��C�����ε{��] �]�w�B�󰱥Ϊ��A => �}��
		final String account = "[your_account]";
		final String password = "[your_password]";
		
		// google��mail host and port(�Ӹ�T�����})
		String mailHost = "smtp.gmail.com";
		String mailPort = "587";
		
		// yahoo��mail host and port(�Ӹ�T�����})
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
			message.setSubject("�{�����ͪ��H��");
			message.setText("���իH��");

			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("�ѵ{���۰ʲ��͡A�ФŦ^�H!!"); //�|��message.setText("���իH��");�o�檺���e�л\��
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			String filename = "file.txt"; //�n���[���ɮ�
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			Transport.send(message);

			log.debug("�H��w���\�H�X");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}