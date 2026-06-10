
public class StringProgram {

	public static void main(String[] args) {
		
		//intern() method
		String str1 = new String("Scaler by InterviewBit").intern();  //Line1  
		String str2 = new String("Scaler by InterviewBit").intern(); //Line2  
		System.out.println(str1 == str2); //prints true  
		
		//palindrome
		String s = "madam";
		StringBuilder sb1 = new StringBuilder(s);
		sb1.reverse();
		if(s.equals(sb1.toString())) {
			System.out.println("true");
		}else {
			System.out.println("false");
		}
	}
}