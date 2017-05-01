import java.util.Arrays;

public class ValueIteration {

	public final double discountFactor;
	public final double probNotBreakOnRock;
	public final double epsilon = 0.0001;
	private final int ROWS = 4;
	private final int COLS = 4;
	private final char[][] types = {{'R', 'R', 'F', 'F'}, {'F', 'F', 'C', 'R'}, {'F', 'R', 'F', 'F'}, {'C', 'R', 'F', 'R'}};
	private final char[] directions = {'W', 'E', 'S', 'N'};
	private final int[][] increments = {{-1, 0}, {+1, 0}, {0, -1}, {0, +1}};
	private final double rewardF = 0;
	private final double rewardR = 1;
	private final double rewardC = 5;
	private final double rewardB = 0;
	private double[][] utility;
	private char[][] action;
	
	public ValueIteration(double discountFactor, double probNotBreakOnRock) {
		this.discountFactor = discountFactor;
		this.probNotBreakOnRock = probNotBreakOnRock;
		action = new char[ROWS][COLS];
		double[][] nextUtility = new double[ROWS][COLS];
		double delta = 0;
		do {
			utility = nextUtility;
			nextUtility = new double[ROWS][COLS];
			delta = 0;
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLS; j++) {
					char bestAction = 'R';
					double maxNextStateUtility = utility[i][j];
					double p = (types[i][j] == 'R') ? probNotBreakOnRock : 1;
					if (types[i][j] != 'C') {
						for (int k = 0; k < directions.length; k++) {
							int r = i + increments[k][0];
							int c = j + increments[k][1];
							if (r >= 0 && r < ROWS && c >= 0 && c < COLS) {
								double u = p * utility[r][c] + (1 - p) * rewardB;
								if (u > maxNextStateUtility) {
									maxNextStateUtility = u;
									bestAction = directions[k];
								}
							}
						}
					}
					nextUtility[i][j] = getReward(i, j) + discountFactor * maxNextStateUtility;
					action[i][j] = bestAction;
					delta = Math.max(delta, Math.abs(utility[i][j] - nextUtility[i][j]));
				}
			}
		} while (delta >= epsilon * (1 - discountFactor) / discountFactor);
	}
	
	private double getReward(int i, int j) {
		if (types[i][j] == 'F') {
			return rewardF;
		}
		else if (types[i][j] == 'R') {
			return rewardR;
		}
		else /* if (types[i][j] == 'C') */ {
			return rewardC;
		}
	}
	
	public double[][] getUtility() {
		return utility;
	}
	
	public char[][] getAction() {
		return action;
	}
	
	public static void main(String[] args) {
		// part b.
		ValueIteration vi = new ValueIteration(0.95, 0.20);
		System.out.println("result for part b");
		System.out.println(Arrays.deepToString(vi.getUtility()));
		System.out.println(Arrays.deepToString(vi.getAction()));
		
		// part c.
		vi = new ValueIteration(0.95, 0.99);
		System.out.println("result for part c");
		System.out.println(Arrays.deepToString(vi.getUtility()));
		System.out.println(Arrays.deepToString(vi.getAction()));
		
		// part d.
		vi = new ValueIteration(0.1, 0.99);
		System.out.println("result for part d");
		System.out.println(Arrays.deepToString(vi.getUtility()));
		System.out.println(Arrays.deepToString(vi.getAction()));	
	}

}
