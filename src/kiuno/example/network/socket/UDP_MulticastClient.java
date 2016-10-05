package kiuno.example.network.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class UDP_MulticastClient {
	private static Logger log = null;
	public static void main(String[] args) throws Exception {
		log = (new MyLogger(UDP_Server.class)).getLogger();
		int mcPort = 12345; //�s��(�s��)��port
		String mcIPStr = "230.1.1.1"; // IP:224.0.0.0 to 239.255.255.255 �i�Ω�s��
		DatagramSocket udpSocket = new DatagramSocket();

		InetAddress mcIPAddress = InetAddress.getByName(mcIPStr);
		String msgTo = "�j�a�n~~";
		byte[] msg = msgTo.getBytes();
		DatagramPacket packet = new DatagramPacket(msg, msg.length);
		
		packet.setAddress(mcIPAddress);
		packet.setPort(mcPort);
		udpSocket.send(packet);

		log.debug("�e�X�@�ӰT���b�s���W:" + msgTo);
		log.debug("���}�{��");
		
		udpSocket.close();
	}
}
