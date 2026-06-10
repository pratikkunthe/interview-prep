import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindFirstRepeatativeCharacterFromString {
	
	public static void main(String[] args) {
		String input = " Java articles are Awesome ";
		 Character c = input.replaceAll("\\s","").chars()
		.mapToObj(s->Character.toLowerCase(Character.valueOf((char)s)))
		.collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
		.entrySet()
		.stream()
		.filter(s->s.getValue() >1)
		.map(s->s.getKey())
		.findFirst()
		.get();
		
		System.out.println(c);
	}
}