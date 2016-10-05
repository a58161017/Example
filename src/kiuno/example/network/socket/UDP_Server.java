package kiuno.example.network.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class UDP_Server {
	private static Logger log = null;
	public static void main(String[] args) throws Exception {
		log = (new MyLogger(UDP_Server.class)).getLogger();
		final int LOCAL_PORT = 12345; //可自行更改沒有在使用的port
		final String SERVER_NAME = "[your_ip]"; // 換成自己的IP位置(可以設定127.0.0.1 or localhost)
		DatagramSocket udpSocket = new DatagramSocket(LOCAL_PORT, InetAddress.getByName(SERVER_NAME));
		udpSocket.setBroadcast(true);
		log.debug("伺服器啟動(UDP協定)");
		log.debug("伺服器連線資訊:" + udpSocket.getLocalSocketAddress());
		while (true) {
			log.debug("等待客戶端的一個UDP封包...");
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024); //封包長度限制1024Byte
			udpSocket.receive(packet);
			String msg = displayPacketDetails(packet); //顯示封包內容
			udpSocket.send(setPacket(packet,msg));
		}
	}

	public static String displayPacketDetails(DatagramPacket packet) {
		byte[] msgBuffer = packet.getData();
		int length = packet.getLength();
		int offset = packet.getOffset();

		int remotePort = packet.getPort();
		InetAddress remoteAddr = packet.getAddress();
		String msg = new String(msgBuffer, offset, length);
		
		log.debug("收到一個封包");
		log.debug("封包資訊:[IP Address=" + remoteAddr + ", port=" + remotePort + ", message=" + msg + "]");
		return msg;
	}

	public static DatagramPacket setPacket(DatagramPacket packetFrom, String msgFrom) throws UnknownHostException {
		int PACKET_MAX_LENGTH = 1024;
		byte[] msgBuffer = ("伺服器已經收到你傳遞的訊息:「"+msgFrom+"」").getBytes();
		
		int length = msgBuffer.length;
		if (length > PACKET_MAX_LENGTH) {
			length = PACKET_MAX_LENGTH;
		}
		DatagramPacket packet = new DatagramPacket(msgBuffer, length);
		
		packet.setAddress(packetFrom.getAddress()); //換成客戶端的IP位置
		packet.setPort(packetFrom.getPort()); //設定客戶端的port
		return packet;
	}
}
