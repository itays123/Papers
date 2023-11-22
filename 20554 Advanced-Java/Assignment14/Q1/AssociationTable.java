import java.util.Iterator;
import java.util.TreeMap;

/**
 * Represents an association table ordered in ascending order\
 * 
 * @author Itay Schechner 328197462
 */
public class AssociationTable<TKey extends Comparable<TKey>, TValue> {
    private TreeMap<TKey, TValue> entries;

    /**
     * Creates an empty association table
     */
    public AssociationTable() {
        this.entries = new TreeMap<TKey, TValue>();
    }

    /**
     * Creates a new association table from two lists
     * 
     * @throws IllegalArgumentException if two lists provided don't have matching
     *                                  order
     */
    public AssociationTable(TKey[] keys, TValue[] values) throws IllegalArgumentException {
        this(); // initialize map

        if (keys.length != values.length)
            throw new IllegalArgumentException("Key list and value list must be of same length");

        for (int i = 0; i < values.length; i++) {
            entries.put(keys[i], values[i]);
        }
    }

    /**
     * Adds the key-value row to the table.
     * If row with key already exists, overrides it.
     */
    public void add(TKey key, TValue value) {
        entries.put(key, value);
    }

    /**
     * Gets the row associated with the key, or null if nonexistent
     */
    public TValue get(TKey key) {
        return entries.get(key);
    }

    /**
     * Checks if the table contains a row associated with given key
     */
    public boolean contains(TKey key) {
        return entries.containsKey(key);
    }

    /**
     * Removes a row associated with given key from the table, if existent.
     * Return true if had existed, false otherwise.
     */
    public boolean remove(TKey key) {
        return entries.remove(key) != null;
    }

    /**
     * Returns the number of rows in the table
     */
    public int size() {
        return entries.size();
    }

    /**
     * Returns an iterator for the keys of the table, in ascending order
     */
    public Iterator<TKey> keyIterator() {
        return entries.keySet().iterator(); // the keyset() method guarantees a sorted order
    }
}
