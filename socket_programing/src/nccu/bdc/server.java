package nccu.bdc;

import java.net.ServerSocket;

public class server extends Thread{
	private static ServerSocket serverSocket;
	
	public server() {
		try {
			serverSocket=new ServerSocket();
		} catch (Exception e) {
			System.out.println("sokcet發生問題");
			System.out.println(e.toString());
		}
	}
	
}
