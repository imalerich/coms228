package edu.iastate.cs228.hw5;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * An implementation of a map based on a splay tree.  
 * 
 * @author Ian Malerich
 *
 */
public class SplayTreeMap<K extends Comparable<? super K>, V>
{
	/**
	 *
	 * The key-value pairs in this Map.
	 *
	 */
	private SplayTreeSet<MapEntry<K, V>> entrySet = new SplayTreeSet<MapEntry<K, V>>();

	/**
	 * Default constructor.  Creates a new, empty, SplayTreeMap
	 */
	public SplayTreeMap() 
	{
		entrySet = new SplayTreeSet<MapEntry<K, V>>();
	}

	/**
	 * Simple copy constructor used only for testing.  This method is fully implemented 
	 * and should not be modified.
	 */
	public SplayTreeMap(SplayTreeSet<MapEntry<K, V>> set) 
	{
		entrySet = set;
	}

	/**
	 *
	 * @return the number of key-value mappings in this map.
	 */
	public int size()
	{
		return entrySet.size(); 
	}

	/**
	 *
	 * @return the value to which key is mapped, or null if this map contains 
	 *         no mapping for key.
	 *
	 */
	public V get(K key)
	{
		if (!containsKey(key))
			return null;
		
		// get the node contains this key and returns its data's (MapEntry) value
		Node<MapEntry<K, V>> n = entrySet.findEntry( new MapEntry<K, V>(key, null));
		return n.getData().value;
	}

	/**
	 *
	 * @return true if this map contains a mapping for key.
	 *
	 */
	public boolean containsKey(K key)
	{
		if (key == null)
			throw new NullPointerException();
		
		return entrySet.contains(key); 
	}

	/**
	 *
	 * Associates value with key in this map.
	 *
	 * @return the previous value associated with key, or null if there was no mapping 
	 *         for key.
	 *
	 */
	public V put(K key, V value)
	{
		if (key == null || value == null)
			throw new NullPointerException();
		
		// if the key is not yet in the map, add it with the supplied data
		if (!containsKey(key)) {
			entrySet.add( new MapEntry<K, V>(key, value));
			
			return null;
		}
		
		// the key already exists, so we must replace its data and return the old data
		Node<MapEntry<K, V>> n = entrySet.findEntry( new MapEntry<K, V>(key, null));
		V v = n.getData().value;
		n.getData().value = value; // set the new data
		
		return v; 
	}

	/**
	 *
	 * Removes the mapping for key from this map if it is present.
	 *
	 * @return the previous value associated with key, or null if there was no mapping 
	 *         for key.
	 *
	 */
	public V remove(K key)
	{
		if (key == null)
			throw new NullPointerException();
		
		// if the key is not yet in the map, there is nothing to remove
		if (!containsKey(key)) {
			return null;
		}
		
		// tell the set to remove the data by passing in a dummy MapEntry with the input key
		Node<MapEntry<K, V>> n = entrySet.findEntry( new MapEntry<K, V>(key, null));
		V v = n.getData().value;
		entrySet.remove( new MapEntry<K, V>(key, null));
		
		return v; 
	}

	/**
	 *
	 * @return a SplayTreeSet storing the keys contained in this map.
	 *
	 */
	public SplayTreeSet<K> keySet()
	{
		// use an iterator to add all keys from this maps set 
		SplayTreeSet<K> tmp = new SplayTreeSet<K>();
		Iterator<MapEntry<K, V>> i = entrySet.iterator();
		while (i.hasNext()) {
			tmp.add(i.next().key);
		}
		
		return tmp; 
	}

	/**
	 *
	 * @return an ArrayList storing the values contained in this map. The values in
	 *         the ArrayList should be arranged in ascending order of the corresponding
	 *         keys.
	 *
	 */
	public ArrayList<V> values()
	{
		// use an iterator to add all keys from this maps set 
		ArrayList<V> tmp = new ArrayList<V>();
		Iterator<MapEntry<K, V>> i = entrySet.iterator();
		while (i.hasNext()) {
			tmp.add(i.next().value);
		}
		
		return tmp;  
	}

}