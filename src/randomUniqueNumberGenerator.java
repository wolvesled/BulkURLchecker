// http://stackoverflow.com/questions/8115722/generating-unique-random-numbers-in-java

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class randomUniqueNumberGenerator {

	public static final int SET_SIZE_REQUIRED = 14776;
	public static final int NUMBER_RANGE = 14776;

	public static void main(String[] args) {
		Random random = new Random();

		Set set = new LinkedHashSet<Integer>(SET_SIZE_REQUIRED);

		while (set.size() < SET_SIZE_REQUIRED) {
			while (set.add(random.nextInt(NUMBER_RANGE)) != true)
				System.out.print(".");
		}
		System.out.println();
		assert set.size() == SET_SIZE_REQUIRED;
		System.out.println(set);
	}
}