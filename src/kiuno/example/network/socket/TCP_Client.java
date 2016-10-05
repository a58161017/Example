package kiuno.example.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

import kiuno.example.logger.MyLogger;

public class TCP_Client {
	private static Logger log = null;
	public static void main(String[] args) throws Exception {
		log = (new MyLogger(TCP_Client.class)).getLogger();
		String IPAddress = "server_ip"; //����server��IP��m
		int port = 12900; //�]�w���A���ݪ�port
		Socket socket = new Socket(IPAddress, port);
		log.debug("�Ȥ�ݱҰ�(TCP��w)");
		log.debug("�Ȥ�ݳs�u��T:" + socket.getLocalSocketAddress());
		BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedWriter socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

		String promptMsg = "�п�J�@�q�T��(Bye to quit):";
		String outMsg = null;

		log.debug(promptMsg);
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
			log.debug("Server: " + inMsg);
			log.debug("");
			log.debug(promptMsg);
		}
		socket.close();
	}
}
