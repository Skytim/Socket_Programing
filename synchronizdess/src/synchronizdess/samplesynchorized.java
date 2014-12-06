package synchronizdess;

public class samplesynchorized {
	public static void main(String[] args) {
		NumberIssue NI;
		Passanger p1, p2, p3, p4;
		NI = new NumberIssue(1, 2);
		p1 = new Passanger(NI);
		p1.start();
		p2 = new Passanger(NI);
		p2.start();
		p3 = new Passanger(NI);
		p3.start();
		p4 = new Passanger(NI);
		p4.start();
		try {
			p1.join();
			p2.join();
			p3.join();
			p4.join();
		} catch (Exception e) {
		}
		System.out.println("Passanger1:Number"+p1.number);
		System.out.println("Passanger2:Number"+p2.number);
		System.out.println("Passanger3:Number"+p3.number);
		System.out.println("Passanger4:Number"+p4.number);
	}
}
