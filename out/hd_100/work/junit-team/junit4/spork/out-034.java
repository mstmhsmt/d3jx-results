package junit.runner;



/**
 * This class defines the current version of JUnit
 */
public class Version {
	private Version() {
		// don't instantiate
	}

	public static String id() {
		return 
<<<<<<< LEFT
4.7.1-SNAPSHOT
=======
4.8.2
>>>>>>> RIGHT
		;
	}

	public static void main(String[] args) {
		System.out.println(id());
	}
}
