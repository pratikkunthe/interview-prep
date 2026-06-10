import java.text.CollationElementIterator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringJava8Practice {
	
	public static void main(String[] args) {
		Stream.of("aa","we","rt","ae").filter(s->s.startsWith("a")).collect(Collectors.toList()).forEach(s->System.out.println("element : "+s));
		Stream.of(10,13,16,24,24).map(i->i+"").filter(s->s.startsWith("1")).forEach(System.out::println);
		//duplicate character in String
		String input = "JavaJavaEEqqff";
		input.chars().mapToObj(s->Character.toLowerCase(Character.valueOf((char)s)))
		.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
		.entrySet()
		.stream()
		.filter(s->s.getValue()>1L).map(Map.Entry::getKey).sorted().collect(Collectors.toList()).forEach(System.out::println);
		
		//toupper case
		Stream.of("aa","we","rt","ae").map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);
		
		Stream.of("AA","WE","RT","AE").map(String::toLowerCase).collect(Collectors.toList()).forEach(System.out::println);
		
		Integer s1 = Stream.of(12,55,78,99,104,406,12,45,56,32).max(Integer::compare).get();
		Integer s2 = Stream.of(12,55,78,99,104,406,12,45,56,32).min(Integer::compare).get();
		System.out.println(s1);
		System.out.println(s2);
		
		
		
		String input1 = "JAVA JAVA sPRING hibernate";
		System.err.println("Demo");
		System.out.println("dummy : "+Arrays.toString(input1.split(" ")));
		Arrays.asList(input1.split(" ")).stream().map(String::toLowerCase).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
		.entrySet().stream().forEach(System.out::println);

		
		String[] aa = {"JAVA","JAVA","sPRING","hibernate"};
		System.out.println("Arrays.stream(aa)");
		Arrays.stream(aa).map(String::toLowerCase).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
		.entrySet().stream().forEach(System.out::println);
		
		Map<String, Long> wordCount = Arrays.asList(input1.split(" ")).stream().map(String::toLowerCase).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
		.entrySet().stream().filter(s->s.getValue()>1L).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	
	System.out.println(wordCount);
	
	
	}
}