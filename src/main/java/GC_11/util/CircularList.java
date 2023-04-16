package GC_11.util;

import java.io.Serializable;
import java.util.ArrayList;

public class CircularList<E> extends ArrayList<E> implements Serializable {
    @Override
    public E get(int index) {
        return super.get(index % size());
    }
}
