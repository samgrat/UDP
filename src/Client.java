import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client implements Runnable {

	private static int MessageID = 0;

	// the data to be sent
	private static String dataS = "Ceci est un test";

	// Arbitary communication port
	private int port = 1234;
	// Server address
	InetAddress adresse;

	DatagramPacket p;
	DatagramSocket s;

	public Client() {

		try {
			// We get local address as the server address
			adresse = InetAddress.getByName("127.0.0.1");
			s = new DatagramSocket();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Send packet
	 */
	public void run() {
		while (true) {
			try {

				// Message conversion
				byte[] data = (dataS + ++MessageID).getBytes();

				// creating the udp packet
				p = new DatagramPacket(data, data.length, adresse, port);

				// Client send its packet
				printSend();
				s.send(p);

				// let's catch server's response
				DatagramPacket response = getServerResponse();
				s.receive(response);

				printReceive();
				printInfoPacket(response);
				printDataPacket(response);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	///////// TODO PARENT class with all the methods bellow /////////

	private DatagramPacket getServerResponse() {

		byte[] buffer2 = new byte[8196];
		DatagramPacket p2 = new DatagramPacket(buffer2, buffer2.length, adresse, port);

		return p2;
	}

	public static synchronized void println(String str) {
		System.err.println(str);
	}

	public synchronized void printSend() {
		println("C" + Thread.currentThread().getId() + " is sending...");
	}
	
	public synchronized void printReceive() {
		println("C" + Thread.currentThread().getId() + " got a message !");
	}

	public synchronized void printInfoPacket(DatagramPacket p) {
		println("C" + Thread.currentThread().getId() + ": Sender's infos are: " + p.getSocketAddress());
	}

	public synchronized void printDataPacket(DatagramPacket p) {
		println("C" + Thread.currentThread().getId() + ": Buffer contains: " + new String(p.getData()));
		println("");
	}

}
