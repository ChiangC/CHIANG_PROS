package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws IOException{
		
		connectServer();
		
	}
	
	private static void connectServer(){
		boolean running = true;
		try {
			Socket socket = new Socket("127.0.0.1", 8899);
			socket.setSoTimeout(10000);
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream outStream = new PrintStream(socket.getOutputStream());
			String msg = null;
			String echo = null;
			while(running){
				System.out.print("Input message:");
				msg = input.readLine();
				if(null ==  msg || "".equals(msg) || "bye".equals(msg)){
					running = false;
				}else{
					outStream.println(msg);
				}
				
				echo = inStream.readLine();
				if(null != echo){
					System.out.println(echo);
				}
			}
			
			if(null != socket){
				socket.close();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			System.out.println("Socket timeout.");
			running = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
