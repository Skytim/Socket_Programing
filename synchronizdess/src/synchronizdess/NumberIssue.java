package synchronizdess;

class NumberIssue {
	int iniNumber, numberInterval;

	public NumberIssue(int iniNumber, int numberInterval) {
		this.iniNumber = iniNumber;
		this.numberInterval = numberInterval;

	}

	//public synchronized int getNumber()
	 public int getNumber()
	{

		int num = iniNumber;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		iniNumber = iniNumber + numberInterval;
		return (num);

	}
}

class Passanger extends Thread {
	int number;
	NumberIssue NI;

	public Passanger(NumberIssue NI) {
		this.NI = NI;
	}

	public void run() {
		number = NI.getNumber();
	}

}
