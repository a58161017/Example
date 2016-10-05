package kiuno.example.network.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class UDP_Client {
	private static Logger log = null;
	public static void main(String[] args) throws Exception {
		log = (new MyLogger(UDP_Client.class)).getLogger();
		DatagramSocket udpSocket = new DatagramSocket();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String promptMsg = "請輸入一段訊息(Bye to quit):";
		String msg = null;
		
		log.debug(promptMsg);
		while ((msg = br.readLine()) != null) {
			if (msg.equalsIgnoreCase("bye")) {
				break;
			}
			
			udpSocket.send(setPacket(msg)); //設定UDP封包，並傳送至伺服器
			DatagramPacket packetFrom = new DatagramPacket(new byte[1024], 1024); //收到的封包長度限制1024Byte
			udpSocket.receive(packetFrom);
			displayPacketDetails(packetFrom);
			log.debug(promptMsg);
		}
		udpSocket.close();
	}

	public static void displayPacketDetails(DatagramPacket packet) {
		byte[] msgBuffer = packet.getData();
		int length = packet.getLength();
		int offset = packet.getOffset();
		int remotePort = packet.getPort();
		InetAddress remoteAddr = packet.getAddress();
		String msg = new String(msgBuffer, offset, length);
		
		log.debug("收到一個封包");
		log.debug("封包資訊:[IP  Address=" + remoteAddr + ", port=" + remotePort + ", message=" + msg + "]");
	}

	public static DatagramPacket setPacket(String msg) throws UnknownHostException {
		String IPAddress = "[server_ip]"; //換成server的IP位置
		int port = 12345; //設定伺服器端的port
		int PACKET_MAX_LENGTH = 1024;
		byte[] msgBuffer = msg.getBytes();
		
		int length = msgBuffer.length;
		if (length > PACKET_MAX_LENGTH) {
			length = PACKET_MAX_LENGTH;
		}
		DatagramPacket packet = new DatagramPacket(msgBuffer, length);
		
		InetAddress serverIPAddress = InetAddress.getByName(IPAddress);
		packet.setAddress(serverIPAddress);
		packet.setPort(port);
		return packet;
	}
}
