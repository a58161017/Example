package kiuno.example.network.socket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class UDP_MulticastServer {
	private static Logger log = null;
	public static void main(String[] args) throws Exception {
		log = (new MyLogger(UDP_MulticastServer.class)).getLogger();
		int mcPort = 12345; //�i�ۦ��port
		String mcIPStr = "230.1.1.1"; // IP:224.0.0.0 to 239.255.255.255 �i�Ω�s��
		InetAddress mcIPAddress = InetAddress.getByName(mcIPStr);
		MulticastSocket mcSocket = new MulticastSocket(mcPort);
		
		log.debug("�s�������̰���b:" + mcSocket.getLocalSocketAddress());
		mcSocket.joinGroup(mcIPAddress); //�N�s��socket�[�J�s�զ�m

		DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
		log.debug("���ݤ@�Ӹs���T��...");
		mcSocket.receive(packet);
		
		String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
		log.debug("[�s��������] ����T��:" + msg);
		log.debug("���}�{��");

		mcSocket.leaveGroup(mcIPAddress);
		mcSocket.close();
	}
}
