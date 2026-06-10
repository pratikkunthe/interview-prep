import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FindNonRepeatativeFirstCharacterFromString {
	
	public static void main(String[] args) {
		
		String input = "Java articles are Awesome";
		Character c = input.chars()
		.mapToObj(s->Character.toLowerCase(Character.valueOf((char)s)))
		.collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
		.entrySet()
		.stream()
		.filter(s->s.getValue()==1L)
		.map(entry->entry.getKey())
		.findFirst().get();
		
		System.out.println("first non-repeating character : "+ c);
		
	}
}