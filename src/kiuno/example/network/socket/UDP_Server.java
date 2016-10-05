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
		final int LOCAL_PORT = 12345; //�i�ۦ���S���b�ϥΪ�port
		final String SERVER_NAME = "[your_ip]"; // �����ۤv��IP��m(�i�H�]�w127.0.0.1 or localhost)
		DatagramSocket udpSocket = new DatagramSocket(LOCAL_PORT, InetAddress.getByName(SERVER_NAME));
		udpSocket.setBroadcast(true);
		log.debug("���A���Ұ�(UDP��w)");
		log.debug("���A���s�u��T:" + udpSocket.getLocalSocketAddress());
		while (true) {
			log.debug("���ݫȤ�ݪ��@��UDP�ʥ]...");
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024); //�ʥ]���׭���1024Byte
			udpSocket.receive(packet);
			String msg = displayPacketDetails(packet); //��ܫʥ]���e
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
		
		log.debug("����@�ӫʥ]");
		log.debug("�ʥ]��T:[IP Address=" + remoteAddr + ", port=" + remotePort + ", message=" + msg + "]");
		return msg;
	}

	public static DatagramPacket setPacket(DatagramPacket packetFrom, String msgFrom) throws UnknownHostException {
		int PACKET_MAX_LENGTH = 1024;
		byte[] msgBuffer = ("���A���w�g����A�ǻ����T��:�u"+msgFrom+"�v").getBytes();
		
		int length = msgBuffer.length;
		if (length > PACKET_MAX_LENGTH) {
			length = PACKET_MAX_LENGTH;
		}
		DatagramPacket packet = new DatagramPacket(msgBuffer, length);
		
		packet.setAddress(packetFrom.getAddress()); //�����Ȥ�ݪ�IP��m
		packet.setPort(packetFrom.getPort()); //�]�w�Ȥ�ݪ�port
		return packet;
	}
}
