import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Test{
	public static void main(String[] args) {
		
		HashMap<Integer, String> hash_map = new HashMap<Integer, String>();
	    hash_map.put(10, "Geeks");
	    System.out.println(hash_map);
	    hash_map.remove(10);
	    System.out.println(hash_map);
	    
	    HashSet<Integer> s= new HashSet<>();
	    s.add(1);
	    System.out.println(s);
	    s.remove(1);
	    System.out.println(s);
	    
	    ArrayList<Integer> i = new ArrayList<Integer>();
	    i.add(15);
	    System.out.println(i);
	    i.remove(0);
	    System.out.println(i);
	    
	}
}