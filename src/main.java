// biblio: https://openclassrooms.com/courses/java-et-la-programmation-reseau/communication-reseau-avec-le-protocole-udp

public class main {

	public static void main(String[] args) {
		
		
		Thread s = new Thread(new Serveur());
		s.start();
		
		Thread c1 = new Thread(new Client());
		c1.start();
	}

}
