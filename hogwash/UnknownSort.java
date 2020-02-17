package hogwash;

import java.util.ArrayList;

/*************************************************************************
 * The story behind this is more or less one of those sudden thoughts "I
 * wonder if this would work." And of course I had to try it, and it seems
 * to work more or less okay, although still pretty poorly. A more time
 * efficient implementation could probably be implemented using arrays and 
 * n extra space. 
 * 
 * After some limited research, I think this is quite similar to a natural
 * merge sort but with a different strategy in the merging. If you have 
 * any idea if it is in fact an implementation of another established 
 * algorithm I would very much want to know.
 ************************************************************************/

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
		if (length < 2) return; // Will always be sorted
		sort(length/2);
	}
	
	
	// Reset the instance variables before each recursive call
	private void reset(int splitIndex) {
		insertionIndex[0] = 0;
		insertionIndex[1] = 0;
		currentInsertion = 0;
		indexA = 0;
		indexB = splitIndex;
	}
	
	
	// Toggle currentInsertion between 0 and 1
	private void switchInsertion() {
		currentInsertion = (currentInsertion + 1) % 2;
	}
	
	
	// If insertion is in first insertion partition, increase both, else only the second
	private void incInsertionIndex() {
		if (currentInsertion == 0) 
			insertionIndex[0]++;
		
		insertionIndex[1]++;
	}
	
	
	private void addA() {
		if (currentInsertion == 0)
			list.add(insertionIndex[currentInsertion], list.remove(indexA)); 
		 // else no actual alteration is necessary, element is already in place
		
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
		
		
		// Take the smallest element of partitions A's and B's first elements (simplifies future operations since there)
		if (list.get(indexA).compareTo(list.get(indexB)) < 0) {
			addA();
		}
		else {
			addB();
		}
		
		
		while (indexA != indexB) {
			if (indexB == length) { 
				// B section is exhausted
				
				if (list.get(indexA).compareTo(list.get(insertionIndex[currentInsertion]-1)) < 0)
					// Top of section A is smaller than the top of current target section
					
					switchInsertion(); // Then start adding to the other target section
					
				addA();
			}
			else if (list.get(indexA).compareTo(list.get(indexB)) >= 0) { 
				// Top of A section is greater than top of B section
				
				if (list.get(indexB).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) { 
					// Top of B section is greater than the top of current target section
					
					addB();
				}
				else if (list.get(indexA).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) {
					// Top of A section is greater than the top of current target section
					
					addA();
				}
				else {
					// Neither A's or B's top element is greater than the top of current target section
					
					switchInsertion(); // Then start adding to the other target section
					
					if (list.get(indexA).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0 &&
							list.get(indexB).compareTo(list.get(insertionIndex[currentInsertion]-1)) < 0 &&
							insertionIndex[0] != insertionIndex[1]) { // There is a true element in the (now) current target section
						// If A's top is greater than the top of (now) current target section, but B's top is not
						
						addA();
					}
					else addB();
				}
			}
			else { 
				// Top of B section is greater than, or equal to, top of A section
				// Otherwise analogous to previous section (but indexA and indexB swapped)
				
				if (list.get(indexA).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) { 
					addA();
				}
				else if (list.get(indexB).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0) {
					addB();
				}
				else {
					switchInsertion();
					
					if (list.get(indexB).compareTo(list.get(insertionIndex[currentInsertion]-1)) >= 0 &&
							list.get(indexA).compareTo(list.get(insertionIndex[currentInsertion]-1)) < 0 &&
							insertionIndex[0] != insertionIndex[1]) {
						addB();
					}
					else addA();
				}
			}
		}
		// Collect the last elements from section B and put in either target section as appropriately
		while (indexB < length) {
			if (list.get(indexB).compareTo(list.get(insertionIndex[currentInsertion]-1)) < 0) {
				switchInsertion();
			}
			addB();
		}
		
		if (insertionIndex[0] != insertionIndex[1]) {
			sort(insertionIndex[0]);
		}
		// else all elements are sorted since no elements have been added to section in interval insertionIndex[0] to insertionIndex[1]
	}
}