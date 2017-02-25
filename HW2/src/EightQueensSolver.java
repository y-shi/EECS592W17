import java.util.ArrayList;
import java.util.Arrays;

public class EightQueensSolver {

	public static final int MAX_RESTARTS = 10;
	
	public static int heuristicCost(int[] state) {
		int cost = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = i + 1; j < 8; j++) {
				if (state[i] == state[j] || Math.abs(state[i] - state[j]) == j - i) {
					cost++;
				}
			}
		}
		return cost;
	}
	
	public static int steepestDescent(int[] state) {
		int moves = 0;
		int currCost = heuristicCost(state);
		while (true) {
			int nextCost = heuristicCost(nextState(state));
			if (nextCost < currCost) {
				currCost = nextCost;
				moves++;
			}
			else {
				break;
			}
		}
		return moves;
	}
	
	public static int[] nextState(int[] state) {
		int currCost = heuristicCost(state);
		int minNextCost = Integer.MAX_VALUE;
		int nextRow = -1;
		int nextCol = -1;
		for (int i = 7; i >= 0; i--) {
			int old = state[i];
			for (int j = 1; j <= 8; j++) {
				if (j == old) {
					continue;
				}
				state[i] = j;
				int cost = heuristicCost(state);
				if (cost < minNextCost) {
					minNextCost = cost;
					nextRow = j;
					nextCol = i;
				}
			}
			state[i] = old;
		}
		if (minNextCost < currCost) {
			state[nextCol] = nextRow;
		}
		return state;
	}
	
	public static Result randomRestartSteepestDescent() {
		int restarts = 0;
		int moves = 0;
		ArrayList<Integer> currentBestSolution = new ArrayList<Integer>();
		int minCost = Integer.MAX_VALUE;
		int[] bestSolution = null;
		boolean isSolution = false;
		do {
			restarts++;
			int[] state = randomState();
			moves += steepestDescent(state);
			int cost = heuristicCost(state);
			if (cost < minCost) {
				minCost = cost;
				bestSolution = state;
			}
			currentBestSolution.add(minCost);
			if (cost == 0) {
				isSolution = true;
				break;
			}
		} while (restarts < MAX_RESTARTS);
		return new Result(restarts, moves, currentBestSolution, bestSolution, isSolution);
	}
	
	public static int[] randomState() {
		int[] state = new int[8];
		for (int i = 0; i < 8; i++) {
			state[i] = (int) (Math.random() * 8 + 1);
		}
		return state;
	}
	
	public static void main(String[] args) {
		System.out.println(heuristicCost(new int[] {1, 1, 1, 1, 1, 1, 1, 1}));
		System.out.println(heuristicCost(new int[] {7, 5, 3, 1, 6, 8, 2, 4}));
		
		int[] state = {1, 1, 1, 1, 1, 1, 1, 1};
		System.out.println("number of moves " + steepestDescent(state));
		System.out.println("final state " + Arrays.toString(state));
		System.out.println("heuristic cost " + heuristicCost(state));
		
		System.out.println("example of a random state " + Arrays.toString(randomState()));
		for (int i = 0; i < 5; i++) {
			Result r = randomRestartSteepestDescent();
			System.out.println("Run " + i);
			System.out.println(r.toString());
		}
	}
	
	public static class Result {
		
		public int restarts;
		public int moves;
		public ArrayList<Integer> currentBestSolution;
		public int[] bestSolution;
		public boolean isSolution;
		
		public Result(int restarts, int moves, ArrayList<Integer> currentBestSolution,
				int[] bestSolution, boolean isSolution) {
			this.restarts = restarts;
			this.moves = moves;
			this.currentBestSolution = currentBestSolution;
			this.bestSolution = bestSolution;
			this.isSolution = isSolution;
		}
		
		@Override
		public String toString() {
			return "restarts " + restarts + "\n" + 
					"Total moves " + moves + "\n" + 
					"CurrentBestSolution " + currentBestSolution.toString() + "\n" + 
					"BestSolution " + Arrays.toString(bestSolution) + "\n" + 
					"isSolution " + isSolution;
		}
		
	}
	
}
