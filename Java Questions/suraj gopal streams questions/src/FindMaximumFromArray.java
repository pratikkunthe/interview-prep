import java.util.stream.Stream;

public class FindMaximumFromArray {

	public static void main(String[] args) {
		Integer s1 = Stream.of(12,55,78,99,104,406,12,45,56,32).max(Integer::compare).get();
		Integer s2 = Stream.of(12,55,78,99,104,406,12,45,56,32).min(Integer::compare).get();
		System.out.println(s1);
		System.out.println(s2);
	}
}