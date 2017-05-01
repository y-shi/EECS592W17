import java.util.ArrayList;
import java.util.List;

public class QLearning {
	
	private final int NUM_OF_STATES = 17;
	private final String[] actions = {"N", "S", "E", "W", "R"};
	private final int MAX_TRAILS = 1000;
	private double[][] Q;
	private double[] rewards;
	
	public QLearning() {
		double alpha = 0.05;
		double gamma = 0.95;
		double explorationChance = 1;
		Q = new double[NUM_OF_STATES][actions.length];
		rewards = new double[MAX_TRAILS];
		int trails = 0;
		int iterations = 0;
		String action = "NONE";
		boolean learning = true;
		Simulator simulator = new Simulator();
		int stateNum = -1;
		while (learning) {
			simulator.commit(action);
			int nextStateNum = simulator.getState();
			double reward = simulator.getReward();
			int index = getActionIndex(action);
			if (index != -1) {
				double q = Q[stateNum][index];
				double qPrime = Q[nextStateNum][getBestActionIndex(nextStateNum)];
				Q[stateNum][index] = q + alpha * (reward + gamma * qPrime - q);
			}
			if (trails == MAX_TRAILS) {
				learning = false;
			}
			else {
				rewards[trails] += reward;
			}
			if (Math.random() < explorationChance) {
				action = actions[(int) (Math.random() * actions.length)];
			}
			else {
				action = actions[getBestActionIndex(nextStateNum)];
			}
			if (!learning) {
				action = "DONE_SIGNAL";
			}
			else if (nextStateNum == 16 || iterations >= 15) {
				action = "RESTART_SIGNAL";
				iterations = 0;
				trails++;
				explorationChance = Math.pow(trails, -0.1);	// FOR PART 1
				// explorationChance = Math.pow(trails, -0.01);	// FOR PART 2
				// explorationChance = Math.pow(trails, -1);	// FOR PART 3
			}
			iterations++;
			stateNum = nextStateNum;
		}
	}
	
	private int getActionIndex(String action) {
		for (int i = 0; i < actions.length; i++) {
			if (actions[i].equals(action)) {
				return i;
			}
		}
		return -1;
	}
	
	private int getBestActionIndex(int stateNum) {
		int ret = -1;
		int count = 1;
		double maxQ = -1;
		for (int i = 0; i < actions.length; i++) {
			if (Q[stateNum][i] > maxQ) {
				ret = i;
				count = 1;
				maxQ = Q[stateNum][i];
			}
			else if (Q[stateNum][i] == maxQ) {
				count++;
				if (Math.random() < 1.0 / count) {
					ret = i;
				}
			}
		}
		return ret;
	}
	
	public double[][] getQ() {
		return Q;
	}
	
	public List<Double> getLast10Rewards() {
		List<Double> l = new ArrayList<Double>();
		for (int i = Math.max(0, rewards.length - 10); i < rewards.length; i++) {
			l.add(rewards[i]);
		}
		return l;
	}
	
	public double getAvgReward() {
		double sum = 0;
		for (double d : rewards) {
			sum += d;
		}
		return sum / rewards.length;
	}
	
	@Override
	public String toString() {
		String s = "Learned policy action.\n";
		for (int i = 0; i < NUM_OF_STATES - 1; i++) {
			s += "State\t" + i + "\t" + actions[getBestActionIndex(i)] + "\n";
		}
		s += "Q-value.\n";
		for (int i = 0; i < NUM_OF_STATES - 1; i++) {
			s += "State\t" + i;
			for (int j = 0; j < actions.length; j++) {
				s += "\t(" + actions[j] + "," + String.format("%.4f", Q[i][j]) + ")";
			}
			s += "\n";
		}
		s += "Rewards of last 10 trails.\n";
		s += getLast10Rewards().toString() + "\n";
		s += "Average reward over all trails.\n";
		s += getAvgReward();
		return s;
	}
	
	public static void main(String[] args) {
		System.out.println(new QLearning().toString());
	}
	
	private class Simulator {
		
		private final char[] states = {'R', 'F', 'F', 'C', 'R', 'F', 'R', 'R',
				'F', 'C', 'F', 'F', 'F', 'R', 'F', 'R', 'B'};
		private final int[][] increments = {{0, +1}, {0, -1}, {+1, 0}, {-1, 0}, {0, 0}};
		private final double probNotBreakOnRock = 0.8;
		private int stateNum;
		private double reward;
		
		public Simulator() {
			commit("RESTART_SIGNAL");
		}
		
		public void commit(String action) {
			if (action.equals("RESTART_SIGNAL")) {
				stateNum = (int) (Math.random() * 16);
				reward = getReward(stateNum);
			}
			else if (stateNum == 16 || action.equals("DONE_SIGNAL")) {
				reward = 0;
			}
			else if (action.equals("R")) {
				reward = getReward(stateNum);
			}
			else {
				int i = getActionIndex(action);
				if (i >= 0 && i < 4) {
					if (states[stateNum] == 'R' && Math.random() >= probNotBreakOnRock) {
						stateNum = 16;
						reward = 0;
					}
					else if (states[stateNum] == 'C') {
						reward = 0;
					}
					else {
						int x = stateNum % 4 + increments[i][0];
						int y = stateNum / 4 + increments[i][1];
						if (x >= 0 && x < 4 && y >= 0 && y < 4) {
							stateNum = x + 4 * y;
							reward = getReward(stateNum);
						}
						else {
							reward = 0;
						}
					}
				}
			}
		}
		
		public int getState() {
			return stateNum;
		}
		
		public double getReward() {
			return reward;
		}
		
		private double getReward(int stateNum) {
			if (states[stateNum] == 'F') {
				return 0;
			}
			else if (states[stateNum] == 'R') {
				return 1;
			}
			else if (states[stateNum] == 'C') {
				return 5;
			}
			else /* if (states[stateNum] == 'B') */ {
				return 0;
			}
		}
	}
	
}
