package socket_programing2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

public class server {

	private final int PORT = 9000;// 伺服器埠號,號碼可自行設定,設定為相衝的
	private final String ADDRESS = "127.0.0.1";// 伺服器位置,預設為本機
	private Vector<SystemChatAssistor> clients;// 儲存客戶端變數
	private InetAddress inetAddress;// 宣告服務端位置變數
	private ServerSocket serverSocket;// Socket變數

	public server() {
		clients = new Vector<SystemChatAssistor>();
		try {
			//獲取伺服器IP位置
			inetAddress = InetAddress.getByName(ADDRESS);

			serverSocket = new ServerSocket(PORT, Integer.MAX_VALUE,
					inetAddress);
			new Thread(runnable).start();
			System.out.println("伺服器已啟動");

		} catch (UnknownHostException ex) {
			System.out.println("伺服器地址位置無效");
		} catch (BindException ex) {
			System.out.println("伺服器網路配置衝突");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private Runnable runnable = new Runnable() {

		public void run() {
			while (true) {
				try {
					synchronized (this) {
						Socket socket = serverSocket.accept();
						
						
						clients.addElement(new SystemChatAssistor(socket));
						
						System.out.println(new StringBuffer(socket
								.getInetAddress().getHostAddress()).append("/")
								.append(socket.getPort()).append("連接."));
						countOnlineUsers();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}
	};

	private void countOnlineUsers() {
		System.out.println("線上人數" + clients.size());

	}

	class SystemChatAssistor {
		private String clientNickName = "System";
		private Socket socket;
		private BufferedReader reader;
		private PrintStream writer;

		SystemChatAssistor(Socket socket) {
			this.socket = socket;
			try {
				// 建立緩衝區,並獲取輸入流
				reader = new BufferedReader(new InputStreamReader(
						socket.getInputStream(), "utf-8"));
				// 建立緩衝區,獲取輸出流

				writer = new PrintStream(socket.getOutputStream(), true,
						"utf-8");
				new Thread(sysAssistor).start();
			} catch (Exception ex) {
				ex.printStackTrace();
				releaseResouces();
			}

		}

		private Runnable sysAssistor = new Runnable() {

			public void run() {
				while (true) {
					try {
						String message = reader.readLine();

						if (message.startsWith("NN-")) {
							message = message.substring(3, message.length());
							registerClient(message);

						} else if (message.startsWith("MSG-")) {
							message = message.substring(3, message.length());
							sendMessageToOnlineClients(clientNickName + ":"
									+ message);
						}

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						releaseResouces();
						break;
					}
				}

			}
		};

		private void sendMessageToOnlineClients(String msg) {
			for (int i = 0; i < clients.size(); i++) {
				SystemChatAssistor client = (SystemChatAssistor) clients.get(i);
				try {
					if (!"".equals(client.clientNickName)) {
						client.writer.println(msg);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}

		private void registerClient(String name) {
			try {
				if (!"".equals(name)) {
					sendMessageToOnlineClients("系統消息" + name + "進入聊天室");
					clientNickName = name;
				} else {
					writer.println("REG");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		private void releaseResouces() {
			try {
				System.out.println(new StringBuffer(socket.getInetAddress()
						.getHostAddress()).append("/").append(socket.getPort())
						.append("切斷").toString());
				sendMessageToOnlineClients("系統消息" + clientNickName + "已經離開");
				socket.close();
				reader.close();
				writer.close();

			} catch (Exception e) {

			} finally {
				socket = null;
				reader = null;
				writer = null;
				clients.remove(this);
				countOnlineUsers();
				System.gc();

			}

		}

	}

	public static void main(String[] args) {
		new server();
	}

}
