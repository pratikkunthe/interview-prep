import java.util.stream.Collectors;
import java.util.stream.Stream;import javax.sql.rowset.spi.SyncFactory;

public class MapToConvertObjectToUpperCaseAndLowerCase {
	public static void main(String[] args) {
		Stream.of("SS","GG","TT","WW","QQ","LL","RR").map(String::toLowerCase).collect(Collectors.toList()).forEach(System.out::println);
		Stream.of("SS","GG","TT","WW","QQ","LL","RR").map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);
		
	}
}