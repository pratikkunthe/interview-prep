import java.util.Arrays;
import java.util.Optional;

public class FindFirstELementOFList {
	
	public static void main(String[] args) {
		Optional<Integer> i = Arrays.asList(12,14,56,87,23).stream().findFirst();
		System.out.println(i.get());
	}
}