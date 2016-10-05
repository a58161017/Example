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
		int mcPort = 12345; //可自行更port
		String mcIPStr = "230.1.1.1"; // IP:224.0.0.0 to 239.255.255.255 可用於群播
		InetAddress mcIPAddress = InetAddress.getByName(mcIPStr);
		MulticastSocket mcSocket = new MulticastSocket(mcPort);
		
		log.debug("群播接收者執行在:" + mcSocket.getLocalSocketAddress());
		mcSocket.joinGroup(mcIPAddress); //將群播socket加入群組位置

		DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
		log.debug("等待一個群播訊息...");
		mcSocket.receive(packet);
		
		String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
		log.debug("[群播接收者] 收到訊息:" + msg);
		log.debug("離開程式");

		mcSocket.leaveGroup(mcIPAddress);
		mcSocket.close();
	}
}
