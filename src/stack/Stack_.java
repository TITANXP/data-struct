package stack;

import java.util.EmptyStackException;

public class Stack_<T> {
    private static class StackNode<T> {
        private T val;
        private StackNode<T> next;

        public StackNode(T val){
            this.val = val;
        }
    }

    private StackNode<T> top;

    public T pop(){
        if(top == null) throw new EmptyStackException();
        T tmp = top.val;
        top = top.next;
        return tmp;
    }

    public void push(T val){
        StackNode<T> node = new StackNode<>(val);
        top.next = node;
        top = node;
    }

    public T peek(){
        if(top == null) throw new EmptyStackException();
        return top.val;
    }

    public boolean isEmpty(){
        return top == null;
    }
}
