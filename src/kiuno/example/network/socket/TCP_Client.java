package kiuno.example.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_Client {
	public static void main(String[] args) throws Exception {
		String IPAddress = "server_ip"; //換成server的IP位置
		int port = 12900; //設定伺服器端的port
		Socket socket = new Socket(IPAddress, port);
		System.out.println("客戶端啟動(TCP協定)");
		System.out.println("客戶端連線資訊:" + socket.getLocalSocketAddress());
		BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

		String promptMsg = "請輸入一段訊息(Bye to quit):";
		String outMsg = null;

		System.out.print(promptMsg);
		while ((outMsg = consoleReader.readLine()) != null) {
			if (outMsg.equalsIgnoreCase("bye")) {
				break;
			}
			// 註:一次只能傳一行文字給伺服器，因為伺服器一次只能讀一行
			socketWriter.write(outMsg);
			socketWriter.write("\n");
			socketWriter.flush();

			// 顯示從伺服器那收到的訊息
			String inMsg = socketReader.readLine();
			System.out.println("Server: " + inMsg);
			System.out.println();
			System.out.print(promptMsg);
		}
		socket.close();
	}
}
