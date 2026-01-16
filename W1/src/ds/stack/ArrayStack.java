package ds.stack;
import java.util.EmptyStackException;

public class ArrayStack<E> implements Stack<E> {

    // holds the stack elements
    private E[] S;

    // index of the top element (-1 when empty)
    private int top = -1;

    // constructor
    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity) {
        S = (E[]) new Object[capacity];
    }

    // number of elements in the stack
    public int size() {
        return top + 1;
    }

    // check if stack is empty
    public boolean isEmpty() {
        return top == -1;
    }

    // push element onto stack
    @Override
    public void push(E o) {
        if (top == S.length - 1) {
            throw new IllegalStateException("Stack is full");
        }
        top++;
        S[top] = o;
    }

    // remove and return top element
    @Override
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E elem = S[top];
        S[top] = null; // avoid loitering
        top--;
        return elem;
    }

    // return top element without removing it
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
