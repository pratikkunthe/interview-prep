import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CountEachLementFromList {
	public static void main(String[] args) {
		Stream.of("AA","WW","AA","GG","OO","PP","WW").collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).entrySet().stream().forEach(System.out::println);
	}
}