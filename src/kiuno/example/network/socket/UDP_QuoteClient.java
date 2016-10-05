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
		String IPAddress = "[server_ip]"; //����server��IP��m
		int port = 12345; //�]�w���A���ݪ�port

		// �ǰe�@�ӫʥ]
		String msg = "���y~~ �ڨӦ����T���q���F~~";
		byte[] msgTo = msg.getBytes();
		InetAddress address = InetAddress.getByName(IPAddress);
		DatagramPacket packet = new DatagramPacket(msgTo, msgTo.length, address, port);
		socket.send(packet);
		log.debug("�o�e�@�q�T�������A��:" + msg);

		// �����@�ӫʥ]
		byte[] msgFrom = new byte[1024];
		packet = new DatagramPacket(msgFrom, msgFrom.length);
		socket.receive(packet);

		// ��ܫʥ]�T��
		String received = new String(packet.getData());
		log.debug("����@�ӫʥ]");
		log.debug("�T�����e:" + received);

		socket.close();
	}
}
