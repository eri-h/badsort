package hogwash;

import java.util.ArrayList;

public class Quicksort<T extends Comparable<T>> implements Sort<T> {
	@Override
	public void sort(ArrayList<T> list){
		sort(list, 0, list.size()-1);
	}
	
	private void sort(ArrayList<T> list, int intervallStart, int intervallEnd){
		if (intervallStart < intervallEnd) {
			int pivotIndex = createPartition(list, intervallStart, intervallEnd);
			sort(list, intervallStart, pivotIndex - 1);
			sort(list, pivotIndex + 1, intervallEnd);
		}
	}
	
	private int createPartition(ArrayList<T> list, int intervallStart, int intervallEnd) {
		T pivot = list.get(intervallEnd);
		int pivotIndex = intervallStart;
		
		for (int currentIndex = intervallStart; currentIndex <= intervallEnd; currentIndex++) {
			if (list.get(currentIndex).compareTo(pivot) < 0) {
				swap(list, currentIndex, pivotIndex);
				pivotIndex++;
			}
		}
		swap(list, pivotIndex, intervallEnd);
		return pivotIndex;
	}
	
	private void swap(ArrayList<T> list, int aIndex, int bIndex) {
		T temp = list.get(aIndex);
		list.set(aIndex, list.get(bIndex));
		list.set(bIndex, temp);
	}
}
