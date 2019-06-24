import java.util.Scanner;

public class Runner {
	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter valid"
				+ " R and S in dot-parenthesis notation"
				+ " separated by a space");
		String str1 = scanner.next();
		String str2 = scanner.next();
		//System.out.println(str1);
		//System.out.println(str2);
		//check for malicious inputs
		//input must contain '(', '.', ')' only
		/*
		if (!str1.matches("(.)") ||
				!str2.matches("(.)"))
		{
			System.out.println("Invalid input. "
					+ "Please enter valid R and S."
					+ "Process terminated.");
			System.exit(0);
		}
		*/
		//parenthesis mismatch
		int countRL = countchar(str1, '(');
		int countRR = countchar(str1, ')');
		int countSL = countchar(str2, '(');
		int countSR = countchar(str2, ')');
		if ((countRL != countRR) || (countSL != countSR))
		{
			System.out.println("Invalid input. "
					+ "Parenthesis mismatch."
					+ "Please enter valid R and S."
					+ "Process terminated.");
			System.exit(0);
		}
		
		Input in = new Input(str1, str2);
		in.translate();
		//System.out.println(Arrays.toString(in.getR()));
		//System.out.println(Arrays.toString(in.getS()));
		Decompose dec = new Decompose(in);
		dec.traverse();
		dec.output();
		scanner.close();
	}
	
	private static int countchar(String s, char c)
	{
		int count = 0;
		for (int i = 0; i < s.length(); i++) {
		    if (s.charAt(i) == c) {
		        count++;
		    }
		}
		return count;
	}
}
