import java.util.Arrays;

public class CardGenerator {

	public static void main(String[] args) {
		String[] suits = {"c", "d", "h", "s"};
		String[] cards = new String[52];
		for (int i = 0, j = 0; j < 4; j++) {
			for (int k = 2; k <= 14; k++) {
				cards[i++] = "\"" + suits[j] + k + "\"";
			}
		}
		System.out.println(Arrays.toString(cards));
	}

}
