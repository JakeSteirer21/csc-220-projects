package prog02;

import java.io.*;

/**
 *
 * @author vjm
 */
public class SortedPD extends ArrayBasedPD {
	public String removeEntry (String name) {
		FindOutput fo = find(name);
		if (!fo.found)
			return null;
		DirectoryEntry entry = theDirectory[fo.index];
		for (int i = fo.index; i < size-1; i++){
			theDirectory[i] = theDirectory[i+1];
		}
		size--;
		modified = true;
		return entry.getNumber();
	}
	protected FindOutput find (String name) {
		int first = 0;
		int last = size -1;
		int middle;
		int cmp;

		while (first <= last ) {
			middle = (first+last)/2;
			cmp = name.compareTo(theDirectory[middle].getName());
            if (cmp == 0) {
            	return new FindOutput(true, middle);
		    } else if (cmp < 0) {
				last = middle - 1; 
			} else if (cmp > 0) {
				first = middle + 1;
			}	
			
		}
		return new FindOutput(false, first);
	}
	protected void add(String name, String number) {
		if (size >= theDirectory.length) {
			reallocate();
		}
		FindOutput fo = find(name);
		for(int i = size - 1; i > fo.index - 1; i--) {
			theDirectory[i + 1] = theDirectory[i];
		}
		theDirectory[fo.index] = new DirectoryEntry(name, number);
		size++;
	}
}
