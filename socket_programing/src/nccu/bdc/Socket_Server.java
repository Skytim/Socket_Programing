package nccu.bdc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Socket_Server extends Thread {
	private static ServerSocket server;
	private static int port = 8888;
	private Socket socket;
	private String message;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	// Constructor
	public Socket_Server() {
		try {
			// create the socket server object
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Socket錯誤!");
			System.out.println("IOException :" + e.toString());
		}
	}

	public void run() {

		System.out.println("server端執行開始!");
		// keep listens
		while (true) {
			System.out.println("Waiting for client request");
			try {
				// 取得server,如果別的thread A已取得,則目前這個thread會等到thread A釋放該server
				synchronized (server) {
					socket = server.accept();
				}
				ois = new ObjectInputStream(socket.getInputStream());
				try {
					message = (String) ois.readObject();
				} catch (java.lang.ClassNotFoundException e) {
				}
				System.out.println("Message Received: " + message);
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject("Hi Client " + message);
				ois.close();
				oos.close();
				socket.close();
			} catch (java.io.IOException e) {
				System.out.println("Socket設定錯誤!");
				System.out.println("IOException :" + e.toString());
			}
			// terminate the server if client sends exit request
			finally {
				if (message.equalsIgnoreCase("exit"))
					break;
			}
		}
		System.out.println("Shutting down Socket server!!");
		// close the ServerSocket object
		try {
			server.close();
		} catch (java.io.IOException e) {
		}
	}

	public static void main(String args[]) {
		(new Socket_Server()).start();
	}
}
