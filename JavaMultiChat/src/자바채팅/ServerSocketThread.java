package 자바채팅;

import java.io.*;
import java.net.*;

public class ServerSocketThread extends Thread{
	Socket socket;
	Server server;
	BufferedReader in;
	PrintWriter out;
	String name;
	String threadName;
	
	public ServerSocketThread(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
		threadName = super.getName();
		System.out.println(socket.getInetAddress() + "입장");//ip주소 get
		System.out.println("threadname : " + threadName);
	}
	public void sendMessage(String str) {
		out.println(str);
	}
	
	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			sendMessage("이름을 입력하세요 : ");
			name = in.readLine();
			server.broadCasting(name + "입장");
			
			while(true) {
				String str_in = in.readLine();
				server.broadCasting(name + " "+str_in);
			}
		}catch(IOException e) {
			System.out.println(threadName + "퇴장 ");
			server.removeClient(this);
		}finally {
			try {
				socket.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
