import java.util.Arrays;

public class AnagramProgramByUsingJava8 {

	public static void main(String[] args) {
		
		String s1="ACb";
		String s2="bca";
		char[] c1=s1.toLowerCase().toCharArray();
		char[] c2=s2.toLowerCase().toCharArray();
		Arrays.sort(c1);
		Arrays.sort(c2);
		System.out.println(Arrays.toString(c1).equals(Arrays.toString(c2)));
	}
}