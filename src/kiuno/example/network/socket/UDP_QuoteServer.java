package kiuno.example.network.socket;

import java.io.IOException;

public class UDP_QuoteServer {
	public static void main(String[] args) throws IOException {
		new UDP_QuoteServerThread().start();
	}
}
