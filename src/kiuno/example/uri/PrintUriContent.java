package kiuno.example.uri;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.log4j.Logger;
import kiuno.example.logger.*;

public class PrintUriContent {
	private static Logger log = null;
	public static String getURLContent(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		URLConnection connection = url.openConnection();
		connection.setDoOutput(true);
		connection.connect();
		OutputStream ous = connection.getOutputStream();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ous));
		bw.write("index.htm");
		bw.flush();
		bw.close();

		printRequestHeaders(connection);
		InputStream ins = connection.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		StringBuffer sb = new StringBuffer();
		String msg = null;
		while ((msg = br.readLine()) != null) {
			sb.append(msg);
			sb.append("\n"); // Append a new line
		}
		br.close();
		return sb.toString();
	}

	public static void printRequestHeaders(URLConnection connection) {
		Map headers = connection.getHeaderFields();
		log.debug(headers);
	}

	public static void main(String[] args) throws Exception {
		log = (new MyLogger(PrintUriContent.class)).getLogger();
		String urlStr = "http://webmail.yuntech.edu.tw";
		String content = getURLContent(urlStr);
		log.debug(content);
	}
}