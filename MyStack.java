/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameModel;

import java.util.EmptyStackException;
import java.util.Vector;

/**
 *
 * @author James freeman (1311990)
 * @version May 2014
 */
public class MyStack<E> extends Vector<E> {

    public MyStack() {
    }

    /**
     * Adds an element to the top of the stack
     *
     * @param item is added to the top of the stack and is returned.
     */
    public E push(E item) {
        addElement(item);
        return item;
    }

    /**
     * Removes and returns the top element of the stack
     *
     * @return the element at the top of the stack that is removed
     */
    public E pop() {
        E obj;
        int len = size();
        obj = peek();
        removeElementAt(len - 1);
        return obj;
    }

    /**
     * Provides a view of the top element currently on the stack
     *
     * @return the element at that is currently at the top of the stack
     */
    public E peek() {
// pre: stack is not empty
// post: top value (next to be popped) is returned
        int len = size();
        if (len == 0) {
            throw new EmptyStackException();
        }
        return elementAt(len - 1);
    }

    /**
     * Confirms if the stack as any elements contained in it.
     *
     * @return true if the stack contains nothing otherwise false.
     */
    public boolean empty() {
// post: returns true if and only if the stack is empty
        return size() == 0;
    }

}
