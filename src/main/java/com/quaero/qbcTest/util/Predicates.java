package com.quaero.qbcTest.util;

import java.util.ArrayList;
import java.util.List;
 
 
public class Predicates {
 
    /**
     * Replicate .NET findAll and <code>Predicate</code>.
     * 
     * @param <T>
     * @param array - an array of objects of type <T>.
     * @param match -
     *            <p>
     *            instance of an implementation of the Predicate
     *            interface.
     *            </p>
     * @return List<T> representing all matches found.
     * @see com.blogspot.crossedlogic.rangesearching.Predicate
     */
    public static <T> List<T> findAll(T[] array,
            Predicate<T> match) {
        List<T> lst = new ArrayList<T>();
 
        for (T obj : array) {
            if (match.isMatch(obj)) {
                lst.add(obj);
            }
        }
 
        return lst;
    }
 
    /**
     * Replicate .NET findAll and <code>Predicate</code>.
     * 
     * @param <T>
     * @param list - collection of objects of type <T>.
     * @param match - instance of an implementation of the
     *            Predicate interface.
     * @return List<T> representing all matches found.
     * @see com.blogspot.crossedlogic.rangesearching.Predicate
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> findAll(List<T> list,
            Predicate<T> match) {
        return findAll((T[]) list.toArray(), match);
    }
}
