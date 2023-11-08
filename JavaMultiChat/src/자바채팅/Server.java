package �ڹ�ä��;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.*;

public class Server {
	ServerSocket serverSocket;
	Socket socket;
	List<Thread> list;
	
	public Server() {
		list = new ArrayList<Thread>();
		System.out.println("���� ���� �Ϸ�");
	}
	
	public void connect() {
		try {
			serverSocket = new ServerSocket(5400);
			serverSocket.setReuseAddress(true);
			
			while(true) {
				socket = serverSocket.accept();
				ServerSocketThread thread = new ServerSocketThread(this, socket);
				addClient(thread);
				thread.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	private synchronized void addClient(ServerSocketThread thread) {
		list.add(thread);
		System.out.println("Ŭ���̾�Ʈ 1�� ���� �� "+list.size()+"��");
	}
	
	public synchronized void removeClient(Thread thread) {
		list.remove(thread);
		System.out.println("Ŭ���̾�Ʈ 1�� ����");
	}
	
	public synchronized void broadCasting(String string) {
		for(int i =0; i<list.size(); i++) {
			ServerSocketThread thread = (ServerSocketThread)list.get(i);
			thread.sendMessage(string);
		}
	}
}
