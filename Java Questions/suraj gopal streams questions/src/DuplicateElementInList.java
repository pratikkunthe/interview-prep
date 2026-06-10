import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class DuplicateElementInList {
	public static void main(String[] args) {
		Set<Integer> s = new HashSet<>();
		Stream.of(1,1,2,3,2,5,5,7,8,9,7,8).filter(i->!s.add(i)).map(Integer::intValue).forEach(System.out::println);
	}
}
