package kiuno.example.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class TCP_Server {
	private static Logger log = null;
	public static void main(String[] args) throws Exception {
		log = (new MyLogger(TCP_Server.class)).getLogger();
		String IPAddress = "your_ip"; //換成自己的IP位置(可以設定127.0.0.1 or localhost)
		int port = 12900; //可自行更改沒有在使用的port
		int backlog = 100; //在佇列端請求連線的最大長度
		/*backlog 範例說明:
			(1)int backlog = 2
			(2)一個client A連接進來，server已經accept成功
			(3)一個client B連接進來，server正在處理A，因此B被放置在佇列中
			(4)一個client C連接進來，server正在處理A，因此C被放置在佇列中
			(5)一個client D連接進來，server正在處理A，但是，佇列已經達到最大值2，因此拒絕D的連線請求
			(6)直到A處理完後，server accept B，也就是將B從佇列取出(FIFO:先進先出)，此時下一個client才能連進來
		*/
		
		ServerSocket serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(IPAddress));
		log.debug("伺服器啟動(TCP協定)");
		log.debug("伺服器連線資訊:" + serverSocket);

		while (true) {
			log.debug("等待客戶端連線...");

			final Socket activeSocket = serverSocket.accept();
			
			log.debug("收到一個客戶端連線");
			log.debug("客戶端連線資訊:" + activeSocket);
			
			// 處理客戶端連線 method1 java8的新寫法
			Runnable runnable = () -> handleClientRequest(activeSocket);
			new Thread(runnable).start(); // 開啟一個執行緒，負責處理一個客戶端連線

			// 處理客戶端連線 method2
			/*
			 * new Thread(new Runnable() { public void run() {
			 * handleClientRequest(activeSocket); } }).start();
			 */
		}
	}
	
	public static void handleClientRequest(Socket socket) {
		try {
			BufferedReader socketReader = null; //客戶端傳遞訊息
			BufferedWriter socketWriter = null; //伺服器回傳訊息
			socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			InetAddress ia = socket.getInetAddress();
			String inMsg = null;
			while ((inMsg = socketReader.readLine()) != null) {
				log.debug("收到客戶端("+ia.getHostAddress()+")的訊息:"+inMsg);

				String outMsg = "伺服器已經收到你傳遞的訊息:「"+inMsg+"」";
				socketWriter.write(outMsg);
				socketWriter.write("\n");
				socketWriter.flush();
			}
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
