package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws Exception{
		ServerSocket serverSocket = new ServerSocket(8899);
		boolean running = true;
		while(running){
			Socket socket = serverSocket.accept();
			System.out.println("Client connected.");
			new Thread(new ServerThread(socket)).start();
		}
		
		serverSocket.close();
	}
}
