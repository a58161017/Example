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
		int mcPort = 12345; //群播(廣播)的port
		String mcIPStr = "230.1.1.1"; // IP:224.0.0.0 to 239.255.255.255 可用於群播
		DatagramSocket udpSocket = new DatagramSocket();

		InetAddress mcIPAddress = InetAddress.getByName(mcIPStr);
		String msgTo = "大家好~~";
		byte[] msg = msgTo.getBytes();
		DatagramPacket packet = new DatagramPacket(msg, msg.length);
		
		packet.setAddress(mcIPAddress);
		packet.setPort(mcPort);
		udpSocket.send(packet);

		log.debug("送出一個訊息在群播上:" + msgTo);
		log.debug("離開程式");
		
		udpSocket.close();
	}
}
