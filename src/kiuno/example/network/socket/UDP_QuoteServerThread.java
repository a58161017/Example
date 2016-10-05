package kiuno.example.network.socket;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class UDP_QuoteServerThread extends Thread {
	protected DatagramSocket socket = null;
	protected BufferedReader in = null;
	protected boolean moreQuotes = true;
	private static Logger log = null;

	public UDP_QuoteServerThread() throws IOException {
		this("UDP_QuoteServerThread");
	}
	
	public UDP_QuoteServerThread(String name) throws IOException {
		super(name);
		log = (new MyLogger(UDP_QuoteServerThread.class)).getLogger();
		String IPAddress = "[your_ip]"; //�����ۤv��IP��m(�i�H�]�w127.0.0.1 or localhost)
		int port = 12345; //�i�ۦ���S���b�ϥΪ�port
		socket = new DatagramSocket(port, InetAddress.getByName(IPAddress));
		log.debug("���A���Ұ�(UDP��w)");
		log.debug("���A���s�u��T:" + socket.getLocalSocketAddress());
		
		try {
			in = new BufferedReader(new FileReader("one-liners.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("�L�k���}�ɮ�");
		}
	}

	public void run() {
		while (moreQuotes) {
			try {
				// �����@�ӫʥ]
				byte[] buf = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				displayPacketDetails(packet);

				// �ˬd���A���O�_�d���T��(�����)�n�q���Ȥ��
				String dString = null;
				if (in == null) dString = new Date().toString();
				else dString = getNextQuote();
				buf = dString.getBytes();

				// �ǰe�@�ӫʥ]
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
				socket.send(packet);
				log.debug("�w�N�T���q���o�e���Ȥ��");
			} catch (IOException e) {
				e.printStackTrace();
				moreQuotes = false;
			}
		}
		socket.close();
	}

	protected String getNextQuote() {
		String returnValue = "";
		try {
			String line = null;
			while ((line = in.readLine()) != null) returnValue += line + "\n";
			if("".equals(returnValue)) returnValue = "�S������T���q��~~";
		} catch (IOException e) {
			returnValue = "���A���o��IOException";
		} finally{
			try{
				if(in != null) in.close();
				moreQuotes = false;
			}catch(Exception e){}
		}
		return returnValue;
	}

	public static String displayPacketDetails(DatagramPacket packet) {
		byte[] msgBuffer = packet.getData();
		int length = packet.getLength();
		int offset = packet.getOffset();

		int remotePort = packet.getPort();
		InetAddress remoteAddr = packet.getAddress();
		String msg = new String(msgBuffer, offset, length);
		
		log.debug("����@�ӫʥ]");
		log.debug("�ʥ]�T��:[IP Address=" + remoteAddr + ", port=" + remotePort + ", message=" + msg + "]");
		return msg;
	}
}
