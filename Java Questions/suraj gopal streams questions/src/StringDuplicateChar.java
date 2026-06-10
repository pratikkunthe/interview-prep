import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringDuplicateChar {
	public static void main(String[] args) {
		String input = "JavaJavaEEqqff";
		
		input.chars()
		.mapToObj(c->Character.toLowerCase(Character.valueOf((char)c)))
		.collect(Collectors.groupingBy(Function.identity(),LinkedHashMap::new,Collectors.counting()))
		.entrySet()
		.stream()
		.filter(map->map.getValue()>1)
		.map(map->map.getKey())
		.forEach(System.out::print);
	}
}