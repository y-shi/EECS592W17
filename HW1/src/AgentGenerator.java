import java.io.FileWriter;

public class AgentGenerator {

	private final static String[] cards = {
			"c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "c11", "c12", "c13", "c14", 
			"d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "d10", "d11", "d12", "d13", "d14", 
			"h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9", "h10", "h11", "h12", "h13", "h14", 
			"s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "s11", "s12", "s13", "s14"};
	private final static String CALL = "call";
	private final static String FOLD = "fold";
	
	private static boolean isPair(int i, int j) {
		return Math.abs(i - j) % 13 == 0;
	}
	
	private static boolean isSameSuit(int i, int j) {
		return i / 13 == j / 13;
	}
	
	private static int distance(int i, int j) {
		return Math.abs((i % 13) - (j % 13));
	}
	
	private static int high(int i, int j) {
		return Math.max(i % 13, j % 13) + 2;
	}
	
	public static void main(String[] args) {
		new Agent1().write("agent1.txt");
		new Agent2().write("agent2.txt");
		new Agent3().write("agent3.txt");
		new Agent4().write("agent4.txt");
	}
	
	public static abstract class Agent {
		
		public abstract boolean call(int i, int j);
		
		public void write(String fileName) {
			try {
				FileWriter out = new FileWriter(fileName);
				for (int i = 0; i < 52; i++) {
					for (int j = 0; j < 52; j++) {
						if (i == j) {
							continue;
						}
						String s = cards[i] + "," + cards[j] + ",";
						if (call(i, j)) {
							s += CALL + "\n";
						}
						else {
							s += FOLD + "\n";
						}
						out.write(s);
					}
				}
				out.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static class Agent1 extends Agent {

		@Override
		public boolean call(int i, int j) {
			return isPair(i, j);
		}
		
	}
	
	public static class Agent2 extends Agent {

		@Override
		public boolean call(int i, int j) {
			return isPair(i, j) || isSameSuit(i, j);
		}
		
	}
	public static class Agent3 extends Agent {

		@Override
		public boolean call(int i, int j) {
			return high(i, j) >= 11;
		}
		
	}
	public static class Agent4 extends Agent {

		@Override
		public boolean call(int i, int j) {
			return isPair(i, j) || high(i, j) >= 11;
		}
		
	}
	
}