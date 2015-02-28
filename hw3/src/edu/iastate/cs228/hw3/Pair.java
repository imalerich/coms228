package edu.iastate.cs228.hw3;

/**
 * Generic wrapper class to encapsulate a pair of data (of the same type).
 * 
 * @author Ian Malerich 
 *
 * @param <T>
 * 	The type of data to be stored.
 * 
 */
class Pair<T> 
{
	// store two elements that are to be kept in a pair.
    private final T first;
    private final T second;

    /**
     * Construct a new pair using the given elements.
     * @param first
     * 	The first element in the pair.
     * @param second
     * 	The second element in the pair.
     */
    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Retrieve the first element.
     * @return	
     * 	The first element.
     */
    public T getFirst() {
        return first;
    }

    /**
     * Retrieve the second element.
     * @return
     * 	The second element.
     */
    public T getSecond() {
        return second;
    }
}

