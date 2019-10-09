/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import material.Position;
import material.tree.binarytree.BinaryTree;

/**
 *
 * @author j.sanchezfe.2018
 */
public class ArrayBinaryTree<E> implements BinaryTree<E> {

    protected ArrayList<Node<E>> array;
    
    public ArrayBinaryTree(){
        array = new ArrayList<>();
    }
    
    @Override
    public Position<E> left(Position<E> v) {
        if(hasLeft(v)){
            Node<E> node = checkTreeNode(v);
            int leftChildRank = leftChildRank(node.getRank());
            return array.get(leftChildRank);
        }else{
            throw new RuntimeException("Position doesn't have right child");
        }
    }

    @Override
    public Position<E> right(Position<E> v) {
        if(hasRight(v)){
            Node<E> node = checkTreeNode(v);
            int rightChildRank = rightChildRank(node.getRank());
            return array.get(rightChildRank);
        }else{
            throw new RuntimeException("Position doesn't have right child");
        }
    }

    @Override
    public boolean hasLeft(Position<E> v) {
        Node<E> node = checkTreeNode(v);
        try{
            int leftChildRank = leftChildRank(node.getRank());
            Node<E> left = array.get(leftChildRank);
            if(left != null) return true;
        }catch(Exception e){
        }
        return false;
    }

    @Override
    public boolean hasRight(Position<E> v) {
        Node<E> node = checkTreeNode(v);
        try{
            int rightChildRank = rightChildRank(node.getRank());
            Node<E> right = array.get(rightChildRank);
            if(right != null) return true;
        }catch(Exception e){
        }
        return false;
    }

    @Override
    public boolean isInternal(Position v) {
        return !isRoot(v) && !isLeaf(v);
    }

    @Override
    public boolean isLeaf(Position p) {
        return !hasLeft(p) && !hasRight(p);
    }

    @Override
    public boolean isRoot(Position p) {
        Node<E> node = checkTreeNode(p);
        return node.getRank() == 0;
    }

    @Override
    public Position<E> root() {
        if(isEmpty()){
            throw new RuntimeException("Tree is empty");
        }else{
            return array.get(0);
        }
    }

    @Override
    public E replace(Position<E> p, E e) {
        Node<E> node = checkTreeNode(p);
        E oldValue = node.getElement();
        node.setElement(e);
        return oldValue;
    }

    @Override
    public Position<E> sibling(Position<E> p) {
        Node<E> node = checkTreeNode(p);
        if(isRoot(node)) throw new RuntimeException("Root node has not siblings");
        Node<E> sibling = array.get(siblingRank(node.getRank()));
        if(sibling != null) return sibling;
        throw new RuntimeException("The position provided has no sibling");
    }

    @Override
    public Position<E> addRoot(E e) {
        Node<E> node = new Node(this,e,0);
        array.set(0, node);
        return node;
    }

    @Override
    public Position<E> insertLeft(Position<E> p, E e) {
        Node<E> node = checkTreeNode(p);
        int leftChildRank = leftChildRank(node.getRank());
        Node<E> left = new Node(this,e,leftChildRank);
        array.set(leftChildRank,left);
        return left;
    }

    @Override
    public Position<E> insertRight(Position<E> p, E e) {
        Node<E> node = checkTreeNode(p);
        int rightChildRank = rightChildRank(node.getRank());
        Node<E> right = new Node(this,e,rightChildRank);
        array.set(rightChildRank,right);
        return right;
    }

    @Override
    public E remove(Position<E> p) {
        Node<E> node = checkTreeNode(p);
        E oldValue = node.getElement();
        Stack<Node<E>> nodesToDelete = new Stack<>();
        nodesToDelete.push(node);
        while(!nodesToDelete.isEmpty()){
            Node<E> aucNode = nodesToDelete.pop();
            if(hasLeft(aucNode)) nodesToDelete.push((Node<E>) left(aucNode));
            if(hasRight(aucNode)) nodesToDelete.push((Node<E>) right(aucNode));
            array.set(aucNode.getRank(), null);
        }
        return oldValue;
    }

    @Override
    public void swap(Position<E> p1, Position<E> p2) {
        Node<E> n1 = checkTreeNode(p1);
        Node<E> n2 = checkTreeNode(p2);
        E aux = n1.getElement();
        n1.setElement(n2.getElement());
        n2.setElement(aux);
    }

    @Override
    public boolean isEmpty() {
        Node<E> raiz = null;
        try{
            raiz = array.get(0);
        }catch(IndexOutOfBoundsException iobe){}
        return raiz == null;
    }

    @Override
    public Position<E> parent(Position<E> v) {
        Node<E> node = checkTreeNode(v);
        if(isRoot(node)) throw new RuntimeException("Root node has no parent");
        return array.get(parentRank(node.getRank()));
    }

    @Override
    public Iterable<? extends Position<E>> children(Position<E> v) {
        Node<E> node = checkTreeNode(v);
        if(isLeaf(node)) throw new RuntimeException("Leaf node have no children");
        ArrayList<Position<E>> children = new ArrayList<>();
        if(hasLeft(node)) children.add(left(node));
        if(hasRight(node)) children.add(right(node));
        return children;
    }

    @Override
    public Iterator<Position<E>> iterator() {
        return new ArrayBinaryTreeIterator();
    }
    
    protected int leftChildRank(int index){
        return index * 2 + 1;
    }
    
    protected int rightChildRank(int index){
        return index * 2 + 2;
    }
    
    protected int siblingRank(int index){
        if(index == 0) throw new RuntimeException("Root node has no siblings");
        if( index % 2 == 0 ){ //Is right child
            return index-1;
        }else{ //Is left child
            return index+1;
        }
    }
    
    protected int parentRank(int index){
        if(index % 2 == 0){ // Is right child
            return  (index-2)/2 ;
        }else{ // Is left child
            return  (index-1)/2 ;
        }
    }
    
    private Node<E> checkTreeNode(Position<E> position){
        if(position instanceof Node){
            Node<E> node = (Node<E>) position;
            if(node.getTree().equals(this)){
                return node;
            }else{
                throw new RuntimeException("The provided Position doesn`t belong to this tree");
            }
        }else{
            throw new RuntimeException("The provided Position is not valid");
        }
    }
    
    private class ArrayBinaryTreeIterator implements Iterator<Position<E>>{

        int index = 0;
        
        @Override
        public boolean hasNext() {
            return index < array.size() && array.get(index) != null;
        }

        @Override
        public Position<E> next() {
            return array.get(index++);
        }
        
    }
    
    protected class Node<E> implements Position<E>{
        private E element;
        private int rank;
        private ArrayBinaryTree tree;
        
        public Node(ArrayBinaryTree tree, E element, int rank){
            this.tree = tree;
            this.element = element;
            this.rank = rank;
        }

        public ArrayBinaryTree getTree() {
            return tree;
        }

        public void setTree(ArrayBinaryTree tree) {
            this.tree = tree;
        }
        
        @Override
        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
        
        public String toString(){
            return element.toString();
        }
        
    }
}
