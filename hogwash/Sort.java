package hogwash;

import java.util.ArrayList;

public interface Sort<T extends Comparable<T>> {
	public abstract void sort(ArrayList<T> list);
	
}
