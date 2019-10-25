package practica1;

import java.util.LinkedList;
import java.util.Queue;
import material.Position;
import material.tree.narytree.NAryTree;

import java.util.Stack;
import material.utils.Pair;

/**
 *
 * @author jvelez
 */
public class TreeUtil {

    /**
     * Clone source in dest. Dest must be a empty tree.
     *
     * @param <T>
     * @param source
     * @param dest
     */
    public static <T> void clone(NAryTree<T> source, NAryTree<T> dest) {
        Queue<Pair<Position<T>, Position<T>>> queueSourcePositions = new LinkedList<>();
        queueSourcePositions.add(new Pair(source.root(), null));

        while (!queueSourcePositions.isEmpty()) {
            Pair<Position<T>, Position<T>> aux = queueSourcePositions.poll();
            Position<T> destPosition;
            if (aux.getSecond() == null) {// Is the root node
                destPosition = dest.addRoot(aux.getFirst().getElement());
            } else {//Is a internal or leaf node
                destPosition = dest.add(aux.getFirst().getElement(), aux.getSecond());
            }

            if (!source.isLeaf(aux.getFirst())) {
                for (Position<T> p : source.children(aux.getFirst())) {
                    queueSourcePositions.add(new Pair(p, destPosition));
                }
            }
        }
    }

    /**
     * Modifies tree to make pos the root maintaining all the distances between
     * nodes.
     *
     * @param <T>
     * @param tree
     * @param pos
     */
    public static <T> void rearranger(NAryTree<T> tree, Position<T> pos) {
        Stack<NAryTree<T>> trees = new Stack<>();
        Position<T> next = pos;

        while (!tree.isRoot(next)) {
            Position<T> aux = tree.parent(next);
            trees.push(tree.subTree(next));
            next = aux;
        }
        trees.push(tree.subTree(next));

        while (trees.size() > 1) {
            NAryTree<T> t1 = trees.pop();
            NAryTree<T> t2 = trees.pop();
            t2.attach(t2.root(), t1);
            trees.push(t2);
        }

        tree.attach(null, trees.pop());

    }

}
