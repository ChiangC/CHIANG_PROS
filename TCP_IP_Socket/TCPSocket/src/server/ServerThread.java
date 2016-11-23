package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerThread implements Runnable {

	private Socket mClient;
	
	public ServerThread(Socket client){
		mClient = client;
	}
	
	@Override
	public void run() {
		
		handlerMessage();
		
	}

	private void handlerMessage(){
		try {
			PrintStream outStream = new PrintStream(mClient.getOutputStream());
			BufferedReader inStream = new BufferedReader(new InputStreamReader(mClient.getInputStream()));
			boolean running = true;
			String msg = null;
			while(running){
				msg = inStream.readLine();
				if(null == msg || "".equals(msg)){
					running = false;
				}else{
					outStream.println("Echo:"+msg);
					if("bye".equals(msg)){
						running = false;
					}		
				}
			}
			outStream.close();
			mClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
