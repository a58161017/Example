package kiuno.example.network.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class UDP_QuoteClient {
	private static Logger log = null;
	public static void main(String[] args) throws IOException {
		log = (new MyLogger(UDP_QuoteClient.class)).getLogger();
		DatagramSocket socket = new DatagramSocket();
		String IPAddress = "[server_ip]"; //換成server的IP位置
		int port = 12345; //設定伺服器端的port

		// 傳送一個封包
		String msg = "哈瞜~~ 我來收取訊息通知了~~";
		byte[] msgTo = msg.getBytes();
		InetAddress address = InetAddress.getByName(IPAddress);
		DatagramPacket packet = new DatagramPacket(msgTo, msgTo.length, address, port);
		socket.send(packet);
		log.debug("發送一段訊息給伺服器:" + msg);

		// 收取一個封包
		byte[] msgFrom = new byte[1024];
		packet = new DatagramPacket(msgFrom, msgFrom.length);
		socket.receive(packet);

		// 顯示封包訊息
		String received = new String(packet.getData());
		log.debug("收到一個封包");
		log.debug("訊息內容:" + received);

		socket.close();
	}
}
