package GC_11.util;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * CircularList is a subclass of ArrayList that provides circular indexing.
 * When accessing elements using the get() method, it automatically wraps around
 * to the beginning of the list if the index exceeds the list size.
 *
 * @param <E> the type of elements in the list
 */
public class CircularList<E> extends ArrayList<E> implements Serializable {

    /**
     * Retrieves the element at the specified index in a circular manner.
     * If the index is larger than the size of the list, it wraps around to the beginning.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     */
    @Override
    public E get(int index) {
        return super.get(index % size());
    }
}
