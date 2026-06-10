import java.util.Comparator;
import java.util.stream.Stream;

public class SortListElement {
	
	public static void main(String[] args) {
		Stream.of(2,4,5,7,1,2,3).sorted().forEach(System.out::print);
		System.out.println("\n");
		Stream.of(2,4,5,7,1,2,3).sorted(Comparator.reverseOrder()).forEach(System.out::print);
	}
}
