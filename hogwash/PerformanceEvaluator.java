package hogwash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;  

public class PerformanceEvaluator {
	public static void main(String[] args) throws IOException {
		PerformanceEvaluator pe = new PerformanceEvaluator();
		
		Bogosort<Integer> bs = new Bogosort<Integer>();
		QuickBogosort<Integer> qbs = new QuickBogosort<Integer>();
		Quicksort<Integer> qs = new Quicksort<Integer>();
		UnknownSort<Integer> us = new UnknownSort<Integer>();
		
		
		Random random = new Random();
		ArrayList<Integer> list;
		
		list = new ArrayList<Integer>();
		for (int k = 0; k < 50; k++) {
			list.add(random.nextInt(100));
		}
		
		
		//list = new ArrayList<Integer>(Arrays.asList(new Integer[] {13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1}));
		
		/*
		for (int i : list) System.out.print(i + " ");
		
		long start, stop;
		start = System.nanoTime();
		us.sort(list);
		stop = System.nanoTime();
		
		System.out.println("\n" + (stop-start));
		for (int i : list) System.out.print(i + " ");
		*/

		
		int[] resultsB = pe.tester(us, 100, 20); 
		for (int i: resultsB) System.out.print(i + " ");
		
		System.out.println(" Done! ");
	}
	
	
	private int[] tester(Sort<Integer> sorter, int maxNumber, int iterations) {
		int[] results = new int[maxNumber-1];
		long start, stop;
		Random random = new Random();
		
		try {
			FileWriter fw = new FileWriter("testresultsUnknown.txt"); 
			
			for (int i = 1; i < maxNumber; i++) {
				int sum = 0;
				
				for (int j = 0; j < iterations; j++) {
					ArrayList<Integer> list = new ArrayList<Integer>();
					for (int k = 0; k < i; k++) list.add(random.nextInt(100) + k*2);
					
					start = System.nanoTime();
					sorter.sort(list);
					stop = System.nanoTime();
					
					sum += stop - start;
				}
				results[i-1] = sum/iterations;
				Integer res = results[i-1];
				fw.write(res.toString());
				fw.write(", ");
				
				
				System.out.println(i);
			}
			fw.close();
			
			
		} 
		catch(Exception e) {
			System.out.println(e);
		} 
		
		return results;
	}
}
