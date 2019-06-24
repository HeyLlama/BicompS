
import java.util.Random;

public class Sampler {
	private int n;

	//chain
	private int[][] record;
	private char[] seq;
	//private char prev;

	//cycle
	private char[] seqC;
	private int[][] recC;
	//private char prevC;
	/*
	 * Sampler constructor
	 * For both chain and cycles
	 */

	public Sampler(int l)
	{
		n = l;
		record = new int[n][5];
		seq =  new char[n];

		recC = new int[n][17];
		seqC = new char[n];
	}

	/**
	 * CHAINS
	 */
	public void computeChain()
	{
		//base cases
		// A|C|G|U
		// 1|1|1|1
		for (int i = 0; i < 4; i++)
		{
			record[0][i] = 1;
		}
		Random rand = new Random();

		//recursion
		// compute the probability matrix
		for (int i = 1; i < n; i++)
		{
			//Detailed version
			//complete the matrix for each n
			// A
			record[i][0] = record[i-1][3];	
			// C
			record[i][1] = record[i-1][2];
			// G
			record[i][2] = record[i-1][1] + record[i-1][3];
			// U
			record[i][3] = record[i-1][0] + record[i-1][2];
		}

		// traceback
		// the first nucleotide
		// A
		int rangeA = record[n-1][0];
		// C
		int rangeC = record[n-1][1];
		// G
		int rangeG = record[n-1][2];
		// U
		int rangeU = record[n-1][3];

		int sum = rangeA + rangeC + rangeG + rangeU;
		int randi = rand.nextInt(sum);
		char nu = 'O';
		//By symmetry, we get the first nucleotide after first iteration
		// A
		if (randi < rangeA)
		{
			nu = 'A';
		}
		// C
		else if (randi < rangeA + rangeC && randi >= rangeA)
		{
			nu = 'C';
		}
		// G
		else if (randi < rangeC + rangeA + rangeG 
				&& randi >= rangeA + rangeC)
		{
			nu = 'G';
		}
		// U
		else
		{
			nu = 'U';
		}
		// fill the first nucleotide
		seq[0] = nu;
		// set the prev for next nucleotide
		char prev = nu;	

		//the rest of the sequence
		char temp = 'O';
		int poi = 0;
		int piv;
		// choose next nucleotide based on previous
		for (int i = n-2; i >= 0; i--)
		{
			if (prev == 'A')
			{
				temp = 'U';
			}
			else if (prev == 'C')
			{
				temp = 'G';
			}
			else if (prev == 'G')
			{
				int rangC = record[i][1];
				int rangU = record[i][3];
				//select a number from 0 to rangeC + rangeU (excl.)
				piv = rand.nextInt(rangC + rangU);
				if (piv < rangC)
				{
					temp = 'C';
				}
				else
				{
					temp = 'U';
				}

			}
			else if (prev == 'U')
			{
				int rangA = record[i][0];
				int rangG = record[i][2];
				piv = rand.nextInt(rangA + rangG);
				if (piv < rangA)
				{
					temp = 'A';
				}
				else
				{
					temp = 'G';
				}
			}

			// fill the next nucleotide
			poi++;
			seq[poi] = temp;
			prev = temp;
		}

		//check sum
		for (int i = 0; i < n; i++)
		{
			record[i][4] = record[i][0] + record[i][1]
					+ record[i][2] + record[i][3];
		}
	}

	public void printSeq()
	{
		System.out.print(String.valueOf(seq));
		System.out.println();
	}

	public void printFibs()
	{
		System.out.print("Probability at each step ");
		for (int i = 0; i < n; i++)
		{
			System.out.print(record[i][4]);
			System.out.print(" ");
			System.out.println();
		}
	}

	public String getSeq()
	{
		return String.valueOf(seq);
	}

	public void printRec()
	{
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < 5; j++)
			{
				System.out.print(record[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
	}

	/**
	 * CYCLES
	 */
	/**
	 * Doc
	 * AA(n + 1) [0]
	 * CC(n + 1) [1]
	 * GG(n + 1) [2]
	 * UU(n + 1) [3]
	 * 
	 * AU(n + 1) [4]
	 * CG(n + 1) [5]
	 * GC(n + 1) [6]
	 * GU(n + 1) [7]
	 * 
	 * AG(n + 1) [8]
	 * AC(n + 1) [9]
	 * CU(n + 1) [10]
	 * CA(n + 1) [11]
	 * 
	 * GA(n + 1) [12]
	 * UA(n + 1) [13]
	 * UC(n + 1) [14]
	 * UG(n + 1) [15]
	 */

	/**
	 * Base case: Starting with one nucleotide
	 * i.e. AA=CC=GG=UU=0
	 * 
	 */
	public String computeCycle()
	{
		Random rand = new Random();
		// for n=1
		if (n%2 == 0)
		{
			return "Input must be odd.";
		}
		// initialize A,C,G,U to 1
		for (int i = 0; i < 4; i++)
		{
			recC[0][i] = 1;
		}
		// else 0

		//for n>1
		//recursion
		// compute the probability matrix
		for (int i = 1; i < n; i++)
		{
			//Detailed version
			//complete the matrix for each n
			// AA
			recC[i][0] = recC[i-1][4];	
			// CC
			recC[i][1] = recC[i-1][5];
			// GG
			recC[i][2] = recC[i-1][6] + recC[i-1][7];
			// UU
			recC[i][3] = recC[i-1][13] + recC[i-1][15];
			// AU
			recC[i][4] = recC[i-1][0] + recC[i-1][8];
			// CG
			recC[i][5] = recC[i-1][1] + recC[i-1][10];
			// GC
			recC[i][6] = recC[i-1][2];
			// GU
			recC[i][7] = recC[i-1][12] + recC[i-1][2];
			// AG
			recC[i][8] = recC[i-1][9] + recC[i-1][4];
			// AC
			recC[i][9] = recC[i-1][8];
			// CU
			recC[i][10] = recC[i-1][11] + recC[i-1][5];									
			// CA
			recC[i][11] = recC[i-1][10];			
			// GA
			recC[i][12] = recC[i-1][7];													
			// UA
			recC[i][13] = recC[i-1][3];															
			// UC
			recC[i][14] = recC[i-1][15];															
			// UG
			recC[i][15] = recC[i-1][14] + recC[i-1][3];																			
		}

		//check sum
		for (int i = 0; i < n; i++)
		{
			int tpsum = 0;
			for (int j = 0; j < 16; j++)
			{
				tpsum += recC[i][j];
			}
			recC[i][16] = tpsum;
		}

		// traceback
		// the first nucleotide
		// AA
		int rangeAA = recC[n-1][0];
		// CC
		int rangeCC = recC[n-1][1];
		// GG
		int rangeGG = recC[n-1][2];
		// UU
		int rangeUU = recC[n-1][3];

		int sum = rangeAA + rangeCC + rangeGG + rangeUU;
		int randi = rand.nextInt(sum);
		char nu = 'O';
		//By symmetry, we get the first nucleotide after first iteration
		// A
		if (randi < rangeAA)
		{
			nu = 'A';
		}
		// C
		else if (randi < rangeAA + rangeCC && randi >= rangeAA)
		{
			nu = 'C';
		}
		// G
		else if (randi < rangeCC + rangeAA + rangeGG
				&& randi >= rangeAA + rangeCC)
		{
			nu = 'G';
		}
		// U
		else
		{
			nu = 'U';
		}
		// fill the first nucleotide
		seqC[0] = nu;
		// set the prev for next nucleotide
		char prev = nu;
		char init = nu;

		//the rest of the sequence
		int poi = 0;
		// choose next nucleotide based on previous
		for (int i = n-2; i >= 0; i--)
		{
			//list of locals
			int rangAA = recC[i][0];
			int rangCC = recC[i][1];
			int rangGG = recC[i][2];
			int rangUU = recC[i][3];
			int rangAU = recC[i][4];
			int rangCG = recC[i][5];
			int rangGC = recC[i][6];
			int rangGU = recC[i][7];
			int rangAG = recC[i][8];
			int rangAC = recC[i][9];
			int rangCU = recC[i][10];									
			int rangCA = recC[i][11];			
			int rangGA = recC[i][12];													
			int rangUA = recC[i][13];															
			int rangUC = recC[i][14];															
			int rangUG = recC[i][15];	

			// for G and U
			String pair = "" + init + prev;
			int pivo = 0;
			// initialize next nucleotide
			char nxt = 'O';
			if (prev == 'A')
			{
				nxt = 'U';
			}
			else if (prev == 'C')
			{
				nxt = 'G';
			}
			// G -> AG + CG + GG + UG
			// need to check the first nucleotide.
			// use string pair defined above to identify
			// which equation to use.
			///////////////////// G
			else if (prev == 'G')
			{
				if (pair.equals("AG"))
				{
					//AC AU
					pivo = rand.nextInt(rangAC + rangAU);
					if (pivo < rangAC)
					{
						nxt = 'C';
					}
					else
					{
						nxt = 'U';
					}
				}

				else if (pair.equals("CG"))
				{
					//CC CU
					pivo = rand.nextInt(rangCC + rangCU);
					if (pivo < rangCC)
					{
						nxt = 'C';
					}
					else
					{
						nxt = 'U';
					}
				}

				else if (pair.equals("GG"))
				{	
					//GC GU
					pivo = rand.nextInt(rangGC + rangGU);
					if (pivo < rangGC)
					{
						nxt = 'C';
					}
					else
					{
						nxt = 'U';
					}
				}

				else if (pair.equals("UG"))
				{
					//UC UU
					pivo = rand.nextInt(rangUC + rangUU);
					if (pivo < rangUC)
					{
						nxt = 'C';
					}
					else
					{
						nxt = 'U';
					}
				}
			}
			// U -> AU + CU + GU + UU
			/////////////////// U
			else if (prev == 'U')
			{
				if (pair.equals("AU"))
				{
					//AA AG
					pivo = rand.nextInt(rangAA + rangAG);
					if (pivo < rangAA)
					{
						nxt = 'A';
					}
					else
					{
						nxt = 'G';
					}
				}

				else if (pair.equals("CU"))
				{
					//CA CG
					pivo = rand.nextInt(rangCA + rangCG);
					if (pivo < rangCA)
					{
						nxt = 'A';
					}
					else
					{
						nxt = 'G';
					}
				}

				else if (pair.equals("GU"))
				{	
					//GA GG
					pivo = rand.nextInt(rangGA + rangGG);
					if (pivo < rangGA)
					{
						nxt = 'A';
					}
					else
					{
						nxt = 'G';
					}
				}

				else if (pair.equals("UU"))
				{
					//UA UG
					pivo = rand.nextInt(rangUA + rangUG);
					if (pivo < rangUA)
					{
						nxt = 'A';
					}
					else
					{
						nxt = 'G';
					}
				}
			}


			// fill the next nucleotide
			poi++;
			seqC[poi] = nxt;
			prev = nxt;
		}
		return null;


	}

	public void checkAACCGGUU()
	{
		for (int i = 0; i < n; i++)
		{
			//cannot form cycle with len 3
			if (i > 3)
			{
				int cycSum = 0;
				cycSum = recC[i][0] + recC[i][1]
						+ recC[i][2] + recC[i][3];
				System.out.println(cycSum);
			}
			else
			{
				//System.out.println(0);
			}
		}
	}

	public void printCFibs()
	{
		System.out.print("Probability at each step ");
		for (int i = 0; i < n; i++)
		{
			System.out.print(recC[i][17]);
			System.out.print(" ");
			System.out.println();
		}
	}

	public void printCRec()
	{
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < 17; j++)
			{
				System.out.print(recC[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
	}
	
	public String getSeqC()
	{
		return String.valueOf(seqC);
	}

}
