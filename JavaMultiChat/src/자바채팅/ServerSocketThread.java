package �ڹ�ä��;

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
		System.out.println(socket.getInetAddress() + "����");//ip�ּ� get
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
			sendMessage("�̸��� �Է��ϼ��� : ");
			name = in.readLine();
			server.broadCasting(name + "����");
			
			while(true) {
				String str_in = in.readLine();
				server.broadCasting(name + " "+str_in);
			}
		}catch(IOException e) {
			System.out.println(threadName + "���� ");
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
