import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class IntTreamExample {
	
	public static void main(String[] args) {
		
		IntStream multipleValeus = IntStream.of(1, 5, 10, 20, 30);
		
		IntStream.range(10, 20).forEach(p->System.out.print(p+" "));
		System.out.println();
		IntStream.rangeClosed(10, 20).forEach(p->System.out.print(p+" "));;
		System.out.println();
		IntStream.iterate(10, i->i+60).limit(6).forEach(p->System.out.print(p+" "));
		System.out.println();
		System.out.println(IntStream.range(10, 20).min().getAsInt());
		System.out.println();
		System.out.println(IntStream.range(10, 20).max().getAsInt());
		
		System.out.println();
		//prime number
		IntStream.range(10, 100).filter(i-> i%2!=0).boxed().collect(Collectors.toList()).forEach(p->System.out.print(p+" "));
		
		
	}
}