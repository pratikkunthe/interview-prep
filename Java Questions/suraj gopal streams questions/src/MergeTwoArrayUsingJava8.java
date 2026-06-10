import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MergeTwoArrayUsingJava8 {

	public static void main(String[] args) {
		
		 Integer[] a = new Integer[]{1,2,3,3}; 
	     Integer[] b = new Integer[]{4,5,6}; 
	     
	     //using flatmap
	     System.out.println(Stream.of(a,b));
	     
	    Object[] arr = Stream.of(a,b).flatMap(Stream::of).distinct().toArray();
	    
	    System.out.println(Arrays.toString(arr));
		
		List<Integer> intList= Stream.of(a,b).flatMap(Stream::of).distinct().collect(Collectors.toList());
		
		System.out.println("merge Array list : "+intList);

	}
}