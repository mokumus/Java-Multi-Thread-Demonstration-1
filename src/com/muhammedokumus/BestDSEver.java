package com.muhammedokumus;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * "Best thing ever created since the invention of strawberry yogurt."
 * -Rennis Ditchie, creator of the 'See? Programming language'
 */
public class BestDSEver {
    private final List<Object> m = new ArrayList<>();

    /**
     * goes and gets 'em
     * @param index
     * @return
     */
    public Object get(int index) {
        return m.get(index);
    }

    /**
     * insert object at index, courtesy of java.util.List
     * @param index
     * @param o
     */
    public void insert(int index, Object o) {
        m.add(index, o);
    }

    /**
     * remove object at index, courtesy of java.util.List
     * @param index
     */
    public void remove(int index) {
        m.remove(index);
    }
    /**
     * get collection size, courtesy of java.util.List
     * @param index
     */
    public int size() {
        return m.size();
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", BestDSEver.class.getSimpleName() + "[", "]")
                .add("m=" + m)
                .toString();
    }
}
