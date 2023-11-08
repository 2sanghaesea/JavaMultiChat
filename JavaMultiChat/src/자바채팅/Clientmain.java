package 자바채팅;

import java.net.*;

public class Clientmain {

	public static void main(String[] args) {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			String ip_str = ia.toString();
			String ip = ip_str.substring(ip_str.indexOf("/")+1);
			new Client(ip,5400);
		}catch(UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
