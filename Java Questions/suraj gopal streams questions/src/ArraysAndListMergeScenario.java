import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ArraysAndListMergeScenario {
	
	public static void main(String[] args) {
		
		int[] a = {1,2,3,4,5,2,1};
		int[] b = {9,2,5,4,7,7,2,1};
		
		IntStream.concat(IntStream.of(a), IntStream.of(b)).boxed().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting())).entrySet().stream().forEach(System.out::println);
		System.out.println("==========================");
		int[] finalList = IntStream.concat(IntStream.of(a), IntStream.of(b)).boxed().distinct().mapToInt(Integer::intValue).toArray();
		System.out.println(Arrays.toString(finalList));
		
		long[] a1 = {1,2,3,4,5,2,1};
		long[] b2 = {9,2,5,4,7,7,2,1};
		
		long[] finalList1 = LongStream.concat(LongStream.of(a1), LongStream.of(b2)).boxed().distinct().mapToLong(Long::longValue).toArray();
		System.out.println(Arrays.toString(finalList1));
		
		List<Integer> integers = new ArrayList<>();
	    integers.add(12);
	    integers.add(13);
	    integers.add(14);
	    
	    List<Integer> integers1 = new ArrayList<>();
	    integers1.add(22);
	    integers1.add(23);
	    integers1.add(24);
	    List<Integer> l=  Stream.of(integers,integers1).flatMap(list->list.stream()).collect(Collectors.toList());
	    System.out.println(l);
	    
	    Integer[] a11 = new Integer[]{1,2,3,3}; 
	    Integer[] b11 = new Integer[]{4,5,6}; 
	    System.out.println("FlatMap Test : "+Arrays.asList(Arrays.asList(a11),Arrays.asList(b11)).stream().flatMap(list->list.stream()).distinct().collect(Collectors.toList()));
	  
	    
	    
	    int[] a2 = {1,2,1,2,3,4,6,6,7,7,7,7,8};
	    Map<Integer,Long> map= new HashMap<>();
		map=Stream.of(1,2,1,2,3,4,6,6,7,7,7,7,8).collect(Collectors.groupingBy(Integer::intValue,Collectors.counting()));
		//System.out.println(map);
		//map=Stream.of("mango","orange","banana","mango","apple","apple","orange","mango","apple","apple","orange","orange","orange","banana","banana","orange","orange","banana").collect(Collectors.groupingBy(String::toString,Collectors.counting()));
		map= Arrays.stream(a2).boxed().collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
		System.out.println(map);
		
		
		/*Notes :
		 - Merge two premitive array use IntStream concat method and boxed method
		 - merge two List object use Stream.of()
		 -if we want to merge two wrapper primitive object then use Arrays.asList
		 -if we want to do operation on premitive array use Arrays.stream and boxed method
		 
		
		
		
		
		*/
		
		
	}
}