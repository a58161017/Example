package kiuno.example.network.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP_Server {
	public static void main(String[] args) throws Exception {
		String IPAddress = "your_ip"; //�����ۤv��IP��m(�i�H�]�w127.0.0.1 or localhost)
		int port = 12900; //�i�ۦ���S���b�ϥΪ�port
		int backlog = 100; //�b��C�ݽШD�s�u���̤j����
		/*backlog �d�һ���:
			(1)int backlog = 2
			(2)�@��client A�s���i�ӡAserver�w�gaccept���\
			(3)�@��client B�s���i�ӡAserver���b�B�zA�A�]��B�Q��m�b��C��
			(4)�@��client C�s���i�ӡAserver���b�B�zA�A�]��C�Q��m�b��C��
			(5)�@��client D�s���i�ӡAserver���b�B�zA�A���O�A��C�w�g�F��̤j��2�A�]���ڵ�D���s�u�ШD
			(6)����A�B�z����Aserver accept B�A�]�N�O�NB�q��C���X(FIFO:���i���X)�A���ɤU�@��client�~��s�i��
		*/
		
		ServerSocket serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(IPAddress));
		System.out.println("���A���Ұ�(TCP��w)");
		System.out.println("���A���s�u��T:" + serverSocket);

		while (true) {
			System.out.println("���ݫȤ�ݳs�u...");

			final Socket activeSocket = serverSocket.accept();
			
			System.out.println("����@�ӫȤ�ݳs�u");
			System.out.println("�Ȥ�ݳs�u��T:" + activeSocket);
			
			// �B�z�Ȥ�ݳs�u method1 java8���s�g�k
			Runnable runnable = () -> handleClientRequest(activeSocket);
			new Thread(runnable).start(); // �}�Ҥ@�Ӱ�����A�t�d�B�z�@�ӫȤ�ݳs�u

			// �B�z�Ȥ�ݳs�u method2
			/*
			 * new Thread(new Runnable() { public void run() {
			 * handleClientRequest(activeSocket); } }).start();
			 */
		}
	}
	
	public static void handleClientRequest(Socket socket) {
		try {
			BufferedReader socketReader = null; //�Ȥ�ݶǻ��T��
			BufferedWriter socketWriter = null; //���A���^�ǰT��
			socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			InetAddress ia = socket.getInetAddress();
			String inMsg = null;
			while ((inMsg = socketReader.readLine()) != null) {
				System.out.println("����Ȥ��("+ia.getHostAddress()+")���T��:"+inMsg);

				String outMsg = "���A���w�g����A�ǻ����T��:�u"+inMsg+"�v";
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
