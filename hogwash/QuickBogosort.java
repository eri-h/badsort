package hogwash;

import java.util.ArrayList;
import java.util.Random;

/*************************************************************************
 * Did you ever ask yourself how you could make bogosort just a little bit 
 * less useless? Well then let me introduce my most recent development 
 * in this area - quick bogosort! [pause for effect]
 * Quick bogosort is a divide-and-conquer implementation of bogosort which
 * utilize a combination of all the good qualities of quicksort in 
 * combination with all the worst qualities of bogosort. 
 * 
 * Estimated average time complexity: O(binomial(n, n/2)*nlog(n)) 
 * Average performance of bogosort: O((n+1)!)
 * 
 * It should now be obvious to you why you should choose qick bogosort 
 * before bogosort since quick bogosort is objectively superior.
 * 
 ************************************************************************/
public class QuickBogosort<T extends Comparable<T>> implements Sort<T> {
	private Random random = new Random();
	
	@Override
	public void sort(ArrayList<T> list){
		sort(list, 0, list.size()-1);
	}
	
	
	public void sort(ArrayList<T> list, int intervallStart, int intervallEnd){
		if (intervallStart < intervallEnd) {
			int pivotIndex = createPartition(list, intervallStart, intervallEnd);
			sort(list, intervallStart, pivotIndex - 1);
			sort(list, pivotIndex + 1, intervallEnd);
		}
	}
	
	
	private int createPartition(ArrayList<T> list, int intervallStart, int intervallEnd) {
		int pivotIndex = random.nextInt((intervallEnd - intervallStart) + 1) + intervallStart;
		
		do { 
			shuffleSegment(list, intervallStart, intervallEnd);
		} while (!isParted(list, intervallStart, intervallEnd, pivotIndex));
		
		return pivotIndex;
	}
	
	
	private void shuffleSegment(ArrayList<T> list, int intervallStart, int intervallEnd) {
		for (int currentIndex = intervallEnd; currentIndex > intervallStart; currentIndex--) {
			int target = random.nextInt((intervallEnd - intervallStart) + 1) + intervallStart;
			
			swap(list, target, currentIndex);
		}
	}
	
	
	private boolean isParted(ArrayList<T> list, int intervallStart, int intervallEnd, int pivotIndex) {
		T pivotValue = list.get(pivotIndex);
		
		for (int currentIndex = intervallStart; currentIndex < pivotIndex; currentIndex++) {
			if (list.get(currentIndex).compareTo(pivotValue) > 0) return false;
		}
		
		for (int currentIndex = pivotIndex; currentIndex < intervallEnd; currentIndex++) {
			if (list.get(currentIndex).compareTo(pivotValue) < 0) return false;
		}
		return true;
	}
	
	
	private void swap(ArrayList<T> list, int aIndex, int bIndex) {
		T temp = list.get(aIndex);
		list.set(aIndex, list.get(bIndex));
		list.set(bIndex, temp);
	}
}



