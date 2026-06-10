import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class CountOfEachCharacterFromString {
	
	public static void main(String[] args) {
		Arrays.stream("string data to count each character".split(""))
		.map(String::toLowerCase)
		.collect(Collectors.groupingBy(str->str,LinkedHashMap::new, Collectors.counting()))
		.entrySet().stream().forEach(System.out::println);
	}
}