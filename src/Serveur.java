import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Serveur implements Runnable {

	/////// DatagramPacket definition ///////
	
	private static byte[] buffer;
	// the data will be added starting at buffer[offset]
	private static int offset;

	/////// DatagramSocket definition ///////
	
	// Arbitary communication port
	private int port;
	// Address specification if server has multiple addresses
	private InetAddress adresse;

	DatagramPacket p;
	DatagramSocket s;
	
	public Serveur() {

		// A pretty large buffer
		buffer = new byte[8192];
		port = 1234;
		
		try {
			// Address specification if server has multiple addresses
			adresse = InetAddress.getByName("127.0.0.1");
			s = new DatagramSocket(port, adresse);
		} catch (IOException e) {

		}

	}

	/**
	 * Receive packets
	 */
	public void run() {

		while (true) {
			try {

				p = new DatagramPacket(buffer, offset, buffer.length);
				
				// Server thread is blocked until it receive packet
				s.receive(p);

				// let's display stuff
				printReceive();
				printInfoPacket();
				printDataPacket();

				// reinit of p lenght
				p.setLength(buffer.length);
				
				printSend();
				s.send(buildServerResponse());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	///////// TODO PARENT class with all the methods bellow /////////

	private DatagramPacket buildServerResponse() {

		byte[] buffer2 = new String("Server Reponse // " + new String (p.getData()) + "! ").getBytes();
		DatagramPacket p2 = new DatagramPacket(buffer2, buffer2.length, p.getAddress(), p.getPort());

		return p2;
	}

	public static synchronized void println(String str) {
		System.err.println(str);
	}

	public synchronized void printSend() {
		println("S" + Thread.currentThread().getId() + " is sending...");
	}
	
	public synchronized void printReceive() {
		println("S" + Thread.currentThread().getId() + " got a message !");
	}
	
	public void printInfoPacket() {
		println("S" + Thread.currentThread().getId() + ": Sender's infos are: " + p.getSocketAddress());
	}

	public void printDataPacket() {
		println("S" + Thread.currentThread().getId() + ": Buffer contains: " + new String(p.getData()));
		println("");
	}

}
