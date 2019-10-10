/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import material.utils.Pair;

/**
 *
 * @author Julian
 */
public class ComparablePair<E1,E2 extends Comparable> extends Pair<E1,E2> implements Comparable<ComparablePair> {

    public ComparablePair(E1 first, E2 second) {
        super(first, second);
    }
    
    @Override
    public int compareTo(ComparablePair o) {
        Comparable c1 = (Comparable) this.getSecond();
        Comparable c2 = (Comparable) o.getSecond();
        return c1.compareTo(c2);
    }
    
}
