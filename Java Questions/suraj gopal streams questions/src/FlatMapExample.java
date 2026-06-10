import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMapExample {

	public static void main(String[] args) {
		
		Integer[] a = new Integer[]{1,2,3,3}; 
	    Integer[] b = new Integer[]{4,5,6}; 
	    
	    List<Integer> a12 =  Arrays.asList(a);
	    
	 //   System.out.println(Arrays.toString(a));
	    
	   List<Integer> a11= Stream.of(1,2,3,4).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
	   Integer[] a22 = a11.stream().toArray(Integer[] :: new);
	   System.out.println("a22 : "+ a22[0]+" "+a22[1]+" "+a22[2]+" "+a22[3]);
	    
	    Stream.of(a).distinct().forEach(System.out::print);
	    List<List<Integer>>listInt = Arrays.asList(Arrays.asList(a),Arrays.asList(b));
	    System.out.println("FlatMap Test : "+listInt.stream().flatMap(list->list.stream()).distinct().collect(Collectors.toList()));
	    
	    List<Integer> integers = new ArrayList<>();
	    integers.add(12);
	    integers.add(13);
	    integers.add(14);
	    
	    List<Integer> integers1 = new ArrayList<>();
	    integers1.add(22);
	    integers1.add(23);
	    integers1.add(24);
	    
	    List<Integer> l=  Stream.of(integers,integers1).flatMap(list->list.stream()).collect(Collectors.toList());
	    System.out.println("final list: "+l);
	    
	}
}