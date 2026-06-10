import java.util.Arrays;
import java.util.stream.IntStream;

public class FIndMaximumElementFromArray {
	public static void main(String[] args) {
		int [] a = {1,3,5,7,8,9,111};
		int max = IntStream.of(a).max().getAsInt();
		System.out.println("max : "+ max);
	}
}