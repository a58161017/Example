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
		String IPAddress = "[your_ip]"; //換成自己的IP位置(可以設定127.0.0.1 or localhost)
		int port = 12345; //可自行更改沒有在使用的port
		socket = new DatagramSocket(port, InetAddress.getByName(IPAddress));
		log.debug("伺服器啟動(UDP協定)");
		log.debug("伺服器連線資訊:" + socket.getLocalSocketAddress());
		
		try {
			in = new BufferedReader(new FileReader("one-liners.txt"));
		} catch (FileNotFoundException e) {
			System.err.println("無法打開檔案");
		}
	}

	public void run() {
		while (moreQuotes) {
			try {
				// 收取一個封包
				byte[] buf = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				displayPacketDetails(packet);

				// 檢查伺服器是否留有訊息(文件檔)要通知客戶端
				String dString = null;
				if (in == null) dString = new Date().toString();
				else dString = getNextQuote();
				buf = dString.getBytes();

				// 傳送一個封包
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				packet = new DatagramPacket(buf, buf.length, address, port);
				socket.send(packet);
				log.debug("已將訊息通知發送給客戶端");
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
			if("".equals(returnValue)) returnValue = "沒有任何訊息通知~~";
		} catch (IOException e) {
			returnValue = "伺服器發生IOException";
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
		
		log.debug("收到一個封包");
		log.debug("封包訊息:[IP Address=" + remoteAddr + ", port=" + remotePort + ", message=" + msg + "]");
		return msg;
	}
}
