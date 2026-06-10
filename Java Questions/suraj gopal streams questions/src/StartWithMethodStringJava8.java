import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StartWithMethodStringJava8 {
	
	public static void main(String[] args) {
		Stream.of(10,13,16,24,24).map(i->i+"").filter(s->s.startsWith("1")).forEach(System.out::println);
	}
}