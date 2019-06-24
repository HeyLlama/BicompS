//import java.util.Arrays;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;


// Note: for convenience purposes
// array starts from 1										`																								
public class Decompose {
	private int[] charR;
	private int[] charS;

	//collections of chain and cycles
	private LinkedList<int[]> chain;
	private LinkedList<int[]> cycle;
	//A list storing all the processed locations
	private int procLen;
	private int n;
	private boolean[] procBool;
	private char[] result;
	// a box of unpaired
	private static final char[] unpairBox =
		{'A', 'G', 'C', 'U'};
	public Decompose(Input in)
	{
		charR = in.getR();
		charS = in.getS();
		n = in.getLen();
		chain = new LinkedList<int[]>();
		cycle = new LinkedList<int[]>();
		// decrease length while processing
		procLen = charR.length;
		result = new char[n+1];
		procBool = new boolean[n+1];

	}

	public void traverse()
	{
		while (processed() == false)
		{
			for (int i = 1; i <= n; i++)
			{
				//skip if already processed
				if (procBool[i] == false)
				{
					boolean falseCy = false;
					//unpaired
					if (charR[i] == 0
							&& charS[i] == 0)
					{
						//immediately output a single nuc
						Random randi = new Random();
						int randInt = randi.nextInt(4);
						char unp = unpairBox[randInt];
						result[i] = unp;
						procBool[i] = true;
						procLen--;
						//System.out.println("unpbool:" + "i="+i);
						//System.out.println("unpbool:" + Arrays.toString(procBool));
					}
					//chain
					else if ((charR[i] != 0
							&& charS[i] == 0) ||
							(charR[i] == 0
							&& charS[i] != 0)
							|| (falseCy == true))

					{
						int[] ch = pairChain(i);
						int chlen = ch.length;
						chain.add(ch);
						procLen -= chlen;
						// processed
						if (falseCy == true)
						{
							falseCy = false;
						}
					}
					//cycle
					else if (charR[i] != 0
							&& charS[i] != 0)
					{
						int[] cy = pairCycle(i);
						if (cy == null)
						{
							falseCy = true;
						}
						else
						{
							int cylen = cy.length;
							procLen -= cylen;
							cycle.add(cy);
						}
					}
				}
			}
		}

	}

	private boolean processed() {
		return (procLen == 1);
	}

	// creates a chain
	// return the length of the chain
	// and adds to the list
	public int[] pairChain(int startIdx)
	{
		String arrStr = Integer.toString(startIdx)+",";
		procBool[startIdx] = true;
		boolean cont = false;
		boolean inR = false;
		int next = 0;
		//put startIdx in the chain array
		// get the next next vertex
		if (charR[startIdx] != 0)
		{
			next = charR[startIdx];
			inR = true;
			cont = true;
			procBool[next] = true;
		}
		else if (charS[startIdx] != 0)
		{
			next = charS[startIdx];
			inR = false;
			cont = true;
			procBool[next] = true;
		}

		// if both non empty 
		// put location in chain
		while (cont == true)
		{
			int tpnext = 1;
			if (charS[next] != 0 
					&& charR[next] != 0)
			{
				boolean tpR = false;
				//if prev in R use idx in S
				if (inR == true)
				{
					tpnext = charS[next];
					tpR = false;
				}
				else 
				{
					tpnext = charR[next];
					tpR = true;
				}
				inR = tpR;
				cont = true;
				arrStr+=Integer.toString(next)+",";
				// set bool to processed
				procBool[tpnext] = true;

			}
			//end of chain
			//terminate
			else
			{
				arrStr+=Integer.toString(next);
				procBool[tpnext] = true;
				cont = false;
				//System.out.println("terminated");
			}
			next = tpnext;
			//System.out.println("Next is:" + next);
		}

		// for testing purposes
		//System.out.println("chain:" + arrStr);
		String[] pairChTemp = arrStr.split(",");
		int[] pairCh = new int[pairChTemp.length];
		for (int i = 0; i < pairChTemp.length; i++)        
		{
			try 
			{
				pairCh[i] = Integer.parseInt(pairChTemp[i]);
			}
			catch (NumberFormatException nfe)   
			{
				pairCh[i] = -1;
			}
		}

		//System.out.println("bool:" + Arrays.toString(procBool));
		return pairCh;


	}
	// creates a cycle
	// return the !!!(length-1) of the cycle
	// and adds to the list
	public int[] pairCycle(int startIdx)
	{
		String arrStr = "";
		procBool[startIdx] = true;
		boolean cont = false;
		int next = 0;
		// Assume starting vertex confirmed
		// put startIdx in the cycle array
		// get the next vertex
		// note: always start with the R plane
		next = charR[startIdx];
		boolean inR = true;
		cont = true;
		boolean falseCy = false;
		// if both non empty 
		// put location in cycle
		while (cont == true)
		{
			int tpnext = 1;
			if (charS[next] != 0 
					&& charR[next] != 0)
			{
				boolean tpR = false;
				//if prev in R use idx in S
				if (inR == true)
				{
					tpnext = charS[next];
					tpR = false;
				}
				else 
				{
					tpnext = charR[next];
					tpR = true;
				}
				inR = tpR;
				cont = true;
				arrStr+=Integer.toString(next);

				//end of cycle
				//terminate
				if (next == startIdx)
				{
					//procBool[next] = true;
					cont = false;
					/*
					if (inR == true)
					{
						falseCy = true;
					}
					 */
					// if arc in R indicating a partial chain
				}
				else
				{
					arrStr+=",";
				}
			}
			// pseudocycle
			// endpoint does not match
			else if (charS[next] == 0 
					|| charR[next] == 0)
			{
				falseCy = true;
				cont = false;
			}
			next = tpnext;
		}
		/*
		if (arrStr.length() == 1)
		{
			falseCy = true;
		}
		 */

		if (falseCy == false)
		{
			// for testing purposes
			//System.out.println("cycle:" + arrStr);


			String[] pairCyTemp = arrStr.split(",");
			int[] pairCy = new int[pairCyTemp.length];
			for (int i = 0; i < pairCyTemp.length; i++)        
			{
				try 
				{
					pairCy[i] = Integer.parseInt(pairCyTemp[i]);
				}
				catch (NumberFormatException nfe)   
				{
					pairCy[i] = -1;
				}
			}

			// set bool to processed
			for (int i = 0; i < pairCy.length; i++)
			{
				procBool[pairCy[i]] = true;
			}
			//System.out.println("bool:" + Arrays.toString(procBool));
			return pairCy;
		}
		else
		{
			return null;
		}
	}

	public String output()
	{
		// chain sampler
		for (Iterator<int[]> i = chain.iterator(); i.hasNext();)
		{
			//System.out.println(i.next());
			int[] curr = i.next();
			int l = curr.length;
			Sampler samCh = new Sampler(l);
			samCh.computeChain();
			char[] ch = samCh.getSeq().toCharArray();
			//System.out.println(Arrays.toString(curr));
			//System.out.println(samCh.getSeq());
			for (int j = 0; j < curr.length; j++)
			{
				result[curr[j]] = ch[j];
			}
		}

		// cycle sampler
		for (Iterator<int[]> i = cycle.iterator(); i.hasNext();)
		{
			
			//System.out.println(i.next());
			int[] curr = i.next();
			//cycle sampler takes length +1
			int l = curr.length+1;
			Sampler samCy = new Sampler(l);
			samCy.computeCycle();
			String cyseq = samCy.getSeqC();
			char[] cy = cyseq.toCharArray();
			//System.out.println(Arrays.toString(curr));
			//System.out.println(cyseq.substring(0, cyseq.length() - 1));
			for (int j = 0; j < curr.length; j++)
			{
				result[curr[j]] = cy[j];
			}
			
		}
		String out = Arrays.toString(result);
		System.out.println(result);
		return out;
	}
}
