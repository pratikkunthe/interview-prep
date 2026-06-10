import java.util.Comparator;
import java.util.stream.Stream;

public class FindSecondHighestNumberFromArray {
	public static void main(String[] args) {
		int a = Stream.of(23,15,11,33,53,90,87).sorted(Comparator.reverseOrder()).skip(1).findFirst().get();
		System.out.println(a);
	}
}