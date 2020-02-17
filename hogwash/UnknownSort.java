package hogwash;

import java.util.ArrayList;


public class UnknownSort<T extends Comparable<T>> implements Sort<T> {
	private int[] insertionIndex = new int[2];
	private int currentInsertion;
	private int indexA;
	private int indexB;
	private ArrayList<T> list;
	private int length;
	
	@Override
	public void sort(ArrayList<T> list) {
		this.length = list.size();
		this.list = list;
		if (length < 2) return;
		sort(length/2);
	}
	
	private void reset(int splitIndex) {
		insertionIndex[0] = 0;
		insertionIndex[1] = 0;
		currentInsertion = 0;
		indexA = 0;
		indexB = splitIndex;
	}
	
	private void switchInsertion() {
		currentInsertion = (currentInsertion + 1) % 2;
	}
	
	private void incInsertionIndex() {
		if (currentInsertion == 0) {
			insertionIndex[0]++;
		}
		insertionIndex[1]++;
	}
	
	private void addA() {
		if (currentInsertion == 0) list.add(insertionIndex[currentInsertion], list.remove(indexA));
		indexA ++;
		
		incInsertionIndex();
	}
	
	
	private void addB() {
		list.add(insertionIndex[currentInsertion], list.remove(indexB));
		indexA ++;
		indexB ++;
		
		incInsertionIndex();
	}
	
	private void sort(int splitIndex) {
		reset(splitIndex);
		
		
		if (list.get(indexA).compareTo(list.get(indexB)) < 0) {
			addA();
		}
		else {
			addB();
		}
		
		
		while (indexA != indexB) {
			if (indexB == length) {
				if (list.get(indexA).compareTo(list.get(insertionIndex[currentInsertion]-1)) < 0) {
					switchInsertion();
				}
					
				addA();
			}
			else if (list.get(indexA).compareTo(list.get(indexB)) >= 0) { //B < A
				if (list.get(indexB).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) { //B > TOP
					addB();
				}
				else if (list.get(indexA).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) {
					addA();
				}
				else {
					switchInsertion();
					
					if (list.get(indexA).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) {
						addA();
					}
					else addB();
				}
			}
			else {
				if (list.get(indexA).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) { //B > TOP
					addA();
				}
				else if (list.get(indexB).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) {
					addB();
				}
				else {
					switchInsertion();
					
					if (list.get(indexB).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) {
						addB();
					}
					else addA();
				}
			}
		}
		while (indexB < length) {
			if (list.get(indexB).compareTo(list.get(insertionIndex[currentInsertion]-1)) < 0) {
				switchInsertion();
			}
			addB();
		}
		
		if (insertionIndex[0] != insertionIndex[1]) {
			sort(insertionIndex[0]);
		}
	}
}