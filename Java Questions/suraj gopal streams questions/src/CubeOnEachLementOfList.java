import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CubeOnEachLementOfList {
	public static void main(String[] args) {
		Stream.of(2,4,5,6,7,1,2,3).map(i->i*i*i).collect(Collectors.toList()).forEach(System.out::println);
	}
}