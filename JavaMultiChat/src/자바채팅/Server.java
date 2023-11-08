package 자바채팅;

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
		System.out.println("서버 구축 완료");
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
		System.out.println("클라이언트 1명 입장 총 "+list.size()+"명");
	}
	
	public synchronized void removeClient(Thread thread) {
		list.remove(thread);
		System.out.println("클라이언트 1명 퇴장");
	}
	
	public synchronized void broadCasting(String string) {
		for(int i =0; i<list.size(); i++) {
			ServerSocketThread thread = (ServerSocketThread)list.get(i);
			thread.sendMessage(string);
		}
	}
}
