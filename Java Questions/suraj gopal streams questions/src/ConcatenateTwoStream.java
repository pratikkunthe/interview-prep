import java.util.Arrays;
import java.util.stream.Stream;

public class ConcatenateTwoStream {
	
	public static void main(String[] args) {
		
		Stream<Integer> i1= Arrays.asList(1,2,3,4,5,6).stream();
		Stream<Integer> i2 = Arrays.asList(12,26,36,47,53,68).stream();
		Stream<Integer> i3 = Stream.concat(i1, i2);
		i3.forEach(System.out::print);
		
	}
}