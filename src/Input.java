import java.util.ArrayDeque;
import java.util.Deque;

public class Input {
	// takes two secondary structures R and S
	// format: ...(.)..
	Deque<Integer> stack;
	private int len;
	private char[] charR;
	private char[] charS;
	private int[] trcharR;
	private int[] trcharS;

	public Input(String r, String s)
	{
		len = r.length();
		charR = r.toCharArray();
		charS = s.toCharArray();
		//leave the first entry blank
		trcharR = new int[len+1];
		trcharS = new int[len+1];
		stack = new ArrayDeque<Integer>();
	}

	// translate input to pairtables
	public void translate()
	{
		//process R
		for (int i = 0; i < len; i++)
		{
			if (charR[i] == '(')
			{
				stack.push(i);
			}
			else if (charR[i] == ')')
			{
				int right = stack.pop();
				int left = i;
				trcharR[left+1] = right+1;
				trcharR[right+1] = left+1;

			}
			else if (charR[i] == '.')
			{
				trcharR[i+1] = 0;
			}
		}
		//process S
		stack = new ArrayDeque<Integer>();
		for (int i = 0; i < len; i++)
		{
			if (charS[i] == '(')
			{
				stack.push(i);
			}
			else if (charS[i] == ')')
			{
				int right = stack.pop();
				int left = i;
				trcharS[left+1] = right+1;
				trcharS[right+1] = left+1;

			}
			else if (charS[i] == '.')
			{
				trcharS[i+1] = 0;
			}
			//System.out.println(Arrays.toString(stack.toArray()));
			//System.out.println("BUG!");

		}
	}

	public int getLen()
	{
		return len;
	}

	public int[] getR()
	{
		return trcharR;
	}

	public int[] getS()
	{
		return trcharS;
	}
}
