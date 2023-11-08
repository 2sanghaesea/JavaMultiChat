package 자바채팅;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
public class Client extends JFrame implements ActionListener, Runnable{
	Container container = getContentPane();
	JTextArea jt = new JTextArea();
	JScrollPane js = new JScrollPane(jt);
	JTextField jtf = new JTextField();
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	String str;
	
	public Client(String ip, int port) {
		setTitle("20184078 유철주");
		setSize(600,400);
		setLocation(400,400);
		init();
		start();
		setVisible(true);
		initNet(ip,port);
		System.out.println("ip : " + ip);
	}
	private void initNet(String ip, int port) {
		try {//서버에 접속을 시도합니다.
			socket = new Socket(ip, port);
			//통신용 클래스를 설정합니다.
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		}catch(UnknownHostException e) {
			System.out.println("IP주소가 다름");
		}catch(IOException e) {
			System.out.println("접속 실패");
		}
		
		//스레드
		Thread thread = new Thread(this);//현재 객체가 run을 받음
		thread.start();
	}
	
	private void init() {
		container.setLayout(new BorderLayout());
		container.add("Center",js);
		container.add("South",jtf);
	}
	
	private void start() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		jtf.addActionListener(this);
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				str = in.readLine();
				jt.append(str+"\n");
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		str = jtf.getText();//서버전송
		out.println(str);
		jtf.setText("");//초기화
		
	}}
