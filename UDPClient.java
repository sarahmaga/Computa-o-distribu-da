import java.net.*;
import java.io.*;

public class UDPClient {
	public static String AugmentStringSize(String str, int size) {
		String result = new String();
		for (int i = 0; i < size; i++) {
			result += str + str;
		}
		return result;
	}

	public static void main(String args[]) {
		// args give message contents and destination hostname
		DatagramSocket aSocket = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			int aux = 10;
			aSocket = new DatagramSocket();
			aSocket.setSoTimeout(100);
			// String newString = AugmentStringSize(args[0], Integer.parseInt(args[2]));
			String message = new String();
			while (true) {
				message = in.readLine();
				message = AugmentStringSize(message,aux);
				
				// byte [] m = newString.getBytes();

				// InetAddress aHost = InetAddress.getByName(args[1]);
				InetAddress aHost = InetAddress.getByName(args[0]);
				int serverPort = 6789;
				DatagramPacket request = new DatagramPacket(message.getBytes(), message.length(), aHost, serverPort);
				aSocket.send(request);
				byte[] buffer = new byte[1000];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(reply);
				System.out.println("Reply: " + new String(reply.getData()));
				aux = aux * 10;
			}

		} catch (SocketException e) {
			System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO: " + e.getMessage());
		} 
		finally {
			
			if (aSocket != null)
				aSocket.close();
		}
	}
}