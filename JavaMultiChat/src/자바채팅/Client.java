package �ڹ�ä��;

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
		setTitle("20184078 ��ö��");
		setSize(600,400);
		setLocation(400,400);
		init();
		start();
		setVisible(true);
		initNet(ip,port);
		System.out.println("ip : " + ip);
	}
	private void initNet(String ip, int port) {
		try {//������ ������ �õ��մϴ�.
			socket = new Socket(ip, port);
			//��ſ� Ŭ������ �����մϴ�.
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
		}catch(UnknownHostException e) {
			System.out.println("IP�ּҰ� �ٸ�");
		}catch(IOException e) {
			System.out.println("���� ����");
		}
		
		//������
		Thread thread = new Thread(this);//���� ��ü�� run�� ����
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
		str = jtf.getText();//��������
		out.println(str);
		jtf.setText("");//�ʱ�ȭ
		
	}}
