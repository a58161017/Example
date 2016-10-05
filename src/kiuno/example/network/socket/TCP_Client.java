package kiuno.example.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_Client {
	public static void main(String[] args) throws Exception {
		String IPAddress = "server_ip"; //����server��IP��m
		int port = 12900; //�]�w���A���ݪ�port
		Socket socket = new Socket(IPAddress, port);
		System.out.println("�Ȥ�ݱҰ�(TCP��w)");
		System.out.println("�Ȥ�ݳs�u��T:" + socket.getLocalSocketAddress());
		BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

		String promptMsg = "�п�J�@�q�T��(Bye to quit):";
		String outMsg = null;

		System.out.print(promptMsg);
		while ((outMsg = consoleReader.readLine()) != null) {
			if (outMsg.equalsIgnoreCase("bye")) {
				break;
			}
			// ��:�@���u��Ǥ@���r�����A���A�]�����A���@���u��Ū�@��
			socketWriter.write(outMsg);
			socketWriter.write("\n");
			socketWriter.flush();

			// ��ܱq���A�������쪺�T��
			String inMsg = socketReader.readLine();
			System.out.println("Server: " + inMsg);
			System.out.println();
			System.out.print(promptMsg);
		}
		socket.close();
	}
}
