package junit.runner;

/**
 * This class defines the current version of JUnit
 */
public class Version {
	private Version() {
		// don't instantiate
	}

	public static String id() {
<<<<<<< commits-hd_100/junit-team/junit4/b5fb9c92dfb1380b8a8b0b53aea3e7f7627ee03c-401be434d95fc1ddcb5f5f258a0f5d3a59505de4/A.java
		return "4.7.1-SNAPSHOT";
||||||| commits-hd_100/junit-team/junit4/b5fb9c92dfb1380b8a8b0b53aea3e7f7627ee03c-401be434d95fc1ddcb5f5f258a0f5d3a59505de4/O.java
		return "4.7";
=======
		return "4.8.2";
>>>>>>> commits-hd_100/junit-team/junit4/b5fb9c92dfb1380b8a8b0b53aea3e7f7627ee03c-401be434d95fc1ddcb5f5f258a0f5d3a59505de4/B.java
	}
	
	public static void main(String[] args) {
		System.out.println(id());
	}
}
