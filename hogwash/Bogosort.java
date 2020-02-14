package hogwash;

import java.util.ArrayList;
import java.util.Random;

public class Bogosort<T extends Comparable<T>> extends Sort<T> {
	private Random random = new Random();
	
	@Override
	public void sort(ArrayList<T> list){
		do { 
			shuffle(list);
		} while (!isSorted(list));
	}
	
	private boolean isSorted(ArrayList<T> list) {
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).compareTo(list.get(i-1)) < 0) return false;
		}
		return true;
	}
	
	private void shuffle(ArrayList<T> list) {
		for (int i = list.size()-1; i > 0; i--) {
			swap(list, random.nextInt(i), i);
		}
	}
	
	
	private void swap(ArrayList<T> list, int a, int b) {
		T temp = list.get(a);
		list.set(a, list.get(b));
		list.set(b, temp);
	}
}
