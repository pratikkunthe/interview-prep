import java.util.stream.Stream;

public class TotalNumberOfElementPresentInList {
	public static void main(String[] args) {
		Long c= Stream.of(1,2,4,6,7,8,0).count();
		System.out.println(c);
	}
}