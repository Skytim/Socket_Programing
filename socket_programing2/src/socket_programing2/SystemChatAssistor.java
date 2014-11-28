package socket_programing2;

import java.awt.print.Printable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SystemChatAssistor {
	private String clientNickName = "System";
	private Socket socket;
	private BufferedReader reader;
	private PrintStream writer;

	SystemChatAssistor(Socket socket) {
		this.socket = socket;
		try {
			//建立緩衝區,並獲取輸入流
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
