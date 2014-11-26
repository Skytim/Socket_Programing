package nccu.bdc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class client {
	private String address;
	private int port = 8888;
	private BufferedReader bufferedReader;
	InetAddress host;
	private Socket client;
	ObjectOutputStream objectOutputStream;
	ObjectInputStream objectInputStream;
	private String msg;

	public client() throws IOException, ClassNotFoundException {
		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("IP address");
		address = bufferedReader.readLine();
		System.out.println("Port");
		port = Integer.parseInt(bufferedReader.readLine());

		host = InetAddress.getByName(address);
		while (true) {
			client = new Socket(host.getHostName(), port);
			objectOutputStream = new ObjectOutputStream(
					client.getOutputStream());
			System.out.println("Sending request to Socket Server");
			msg = bufferedReader.readLine();
			objectOutputStream.writeObject(msg);
			objectInputStream = new ObjectInputStream(client.getInputStream());
			msg = (String) objectInputStream.readObject();
			System.out.println("Server Response" + msg);
			objectInputStream.close();
			objectOutputStream.close();
			if (msg.equalsIgnoreCase("Hi client exit")) {

				System.out.println("Shutting down Socket client!!");
				client.close();
				return;

			}

		}
	}

	public static void main(String args[]) throws IOException,
			ClassNotFoundException {
		new client();
	}
}
