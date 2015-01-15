package prog04;

/** FindOutput contains the output of find: boolean found and DLLNode
 * entry.  If the entry is found, found is true and entry points to its
 * DLLNode.  If the entry is not found, found if false and entry points
 * to the DLLNode which would be next after the entry if it is added.
 * If the new entry should go at the end (tail), then entry is set to
 * null since no DLLNode should come next after it.
 * @author vjm
 */

public class FindOutput {
	/** True if entry if found. */
	public final boolean found;

	/** The DLLNode the entry if it is there or the next one after
	 * it if it is not.
	 */
	public final DLLNode entry;

	FindOutput (boolean found, DLLNode entry) {
		this.found = found;
		this.entry = entry;
	}
}

