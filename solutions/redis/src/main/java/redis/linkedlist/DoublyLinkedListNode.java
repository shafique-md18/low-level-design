package redis.linkedlist;

public class DoublyLinkedListNode<E> {
    private DoublyLinkedListNode<E> prev;
    private DoublyLinkedListNode<E> next;
    E element;

    public DoublyLinkedListNode(E element) {
        this.element = element;
        this.prev = null;
        this.next = null;
    }

    public DoublyLinkedListNode<E> getPrev() {
        return prev;
    }

    public void setPrev(DoublyLinkedListNode<E> prev) {
        this.prev = prev;
    }

    public DoublyLinkedListNode<E> getNext() {
        return next;
    }

    public void setNext(DoublyLinkedListNode<E> next) {
        this.next = next;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }
}
