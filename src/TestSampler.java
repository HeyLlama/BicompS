// NOTE: INPUT INTEGER MUST BE ODD
import java.util.*;

import org.junit.jupiter.api.Test;
public class TestSampler {

	private Map<String, Integer> counts;
	private Map<String, Integer> countsC;
	private Map<String, Integer> countsCy;
	private int len;
	/**
	 * Tests the sampler
	 * 
	 */
	//@Test
	public void testSam()
	{
		len = 7;
		Sampler sam = new Sampler(len);
		sam.computeChain();
		sam.printSeq();
		sam.printFibs();
		sam.printRec();
	}

	//@Test
	public void test2Sam()
	{
		len = 5;
		//TEST
		List<String> list = new ArrayList<String>();
		List<String> listC = new ArrayList<String>();
		int n = 2600;
		for (int i = 0; i < n; i++)
		{
			Sampler sam = new Sampler(len);
			sam.computeChain();
			//sam.printSeq();
			//sam.printFibs();
			sam.computeCycle();
			list.add(sam.getSeq());
			listC.add(sam.getSeqC());
		}
		// ref: https://stackoverflow.com/questions/44750278/how-to-count-the-amount-of-each-object-in-an-array-list-java
		///CHAIN
		counts = new HashMap<>();
		for (String x : new HashSet<>(list))
		{
			counts.put(x, Collections.frequency(list, x));
		}
		System.out.println("Chain counts");
		System.out.println(counts);
		System.out.println();
		System.out.println(counts.size());

		//map for cycles counts in chain list
		countsC = new HashMap<>();
		for (Map.Entry<String,Integer> entry : counts.entrySet())
		{
			char [] tp = entry.getKey().toCharArray();
			if (tp[0] == tp[len-1])
			{
				countsC.put(entry.getKey(), entry.getValue());
			}
		}
		System.out.println("Cycles counts from chain computation");
		System.out.println(countsC);
		System.out.println();
		System.out.println(countsC.size());

		///CYCLE
		countsCy = new HashMap<>();
		for (String x : new HashSet<>(listC))
		{
			countsCy.put(x, Collections.frequency(listC, x));
		}
		System.out.println("Cycles counts");
		System.out.println(countsCy);
		System.out.println();
		System.out.println(countsCy.size());

	}

	//@Test
	public void testCSam()
	{
		len = 5;
		Sampler sam = new Sampler(len);
		sam.computeCycle();
		sam.printCRec();
		sam.checkAACCGGUU();
	}

	/**
	 * Expect to get lists of non overlapping
	 *  cycles and chains
	 */
	//@Test
	public void testDecom()
	{
		String str1 = "((..).(.))..";
		String str2 = "(.)..(.)(.).";
		Input in = new Input(str1, str2);
		in.translate();
		System.out.println(Arrays.toString(in.getR()));
		System.out.println(Arrays.toString(in.getS()));
		Decompose dec = new Decompose(in);
		dec.traverse();

	}

	/**
	 * Expect to get lists of non overlapping
	 *  cycles and chains
	 *  a cycle and a chain
	 *  
	 */
	//@Test
	public void testDecom2()
	{
		String str1 = ".(.)(())";
		String str2 = ".(.(.)).";
		Input in = new Input(str1, str2);
		in.translate();
		System.out.println(Arrays.toString(in.getR()));
		System.out.println(Arrays.toString(in.getS()));
		Decompose dec = new Decompose(in);
		dec.traverse();

	}

	/**
	 * Expect to get lists of non overlapping
	 *  cycles and chains
	 *  a cycle and a chain
	 *  
	 */
	
	@Test
	public void testDecom3()
	{
		String str1 = "((.)..)..";
		String str2 = "(....)(.)";
		Input in = new Input(str1, str2);
		in.translate();
		//System.out.println(Arrays.toString(in.getR()));
		//
		System.out.println(Arrays.toString(in.getS()));
		Decompose dec = new Decompose(in);
		dec.traverse();
		dec.output();
	}
	//@Test
	public void testDecom4()
	{
		String str1 = "(..)";
		String str2 = "(.).";
		Input in = new Input(str1, str2);
		in.translate();
		//System.out.println(Arrays.toString(in.getR()));
		//System.out.println(Arrays.toString(in.getS()));
		Decompose dec = new Decompose(in);
		dec.traverse();
		dec.output();
	}

	// Uniformity test
	//@Test
	public void testUnifCh()
	{
		String str1 = "(..)";
		String str2 = "(.).";
		Input in = new Input(str1, str2);
		in.translate();
		//System.out.println(Arrays.toString(in.getR()));
		//System.out.println(Arrays.toString(in.getS()));
		Decompose dec = new Decompose(in);

		// chain only
		List<String> list = new ArrayList<String>();
		int n = 100000;
		for (int i = 0; i < n; i++)
		{
			dec.traverse();
			String res = dec.output();
			list.add(res);
		}
		// ref: https://stackoverflow.com/questions/44750278/how-to-count-the-amount-of-each-object-in-an-array-list-java
		///CHAIN
		counts = new HashMap<>();
		for (String x : new HashSet<>(list))
		{
			counts.put(x, Collections.frequency(list, x));
		}
		System.out.println("Chain counts");
		System.out.println(counts);
		System.out.println();
		System.out.println(counts.size());
	}

	//@Test
	public void testUnifCy()
	{
		String str1 = "(..)";
		String str2 = "(..)";
		Input in = new Input(str1, str2);
		in.translate();
		//System.out.println(Arrays.toString(in.getR()));
		//System.out.println(Arrays.toString(in.getS()));
		Decompose dec = new Decompose(in);

		// cycle only
		List<String> list = new ArrayList<String>();
		int n = 100000;
		for (int i = 0; i < n; i++)
		{
			dec.traverse();
			String res = dec.output();
			list.add(res);
		}

		///CYCLE
		countsCy = new HashMap<>();
		for (String x : new HashSet<>(list))
		{
			countsCy.put(x, Collections.frequency(list, x));
		}
		System.out.println("Cycles counts");
		System.out.println(countsCy);
		System.out.println();
		System.out.println(countsCy.size());
	}

}
