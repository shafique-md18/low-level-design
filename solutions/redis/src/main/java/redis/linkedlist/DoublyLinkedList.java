package redis.linkedlist;

public class DoublyLinkedList<E> {
    private final DoublyLinkedListNode<E> dummyHead;
    private final DoublyLinkedListNode<E> dummyTail;

    public DoublyLinkedList() {
        dummyHead = new DoublyLinkedListNode<>(null);
        dummyTail = new DoublyLinkedListNode<>(null);

        dummyHead.setNext(dummyTail);
        dummyTail.setPrev(dummyHead);
    }

    public void detach(DoublyLinkedListNode<E> node) {
        if (node != null) {
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
        }
    }

    public DoublyLinkedListNode<E> addLast(DoublyLinkedListNode<E> node) {
        node.setNext(dummyTail);
        node.setPrev(dummyTail.getPrev());
        dummyTail.getPrev().setNext(node);
        dummyTail.setPrev(node);
        return node;
    }

    public DoublyLinkedListNode<E> addLast(E val) {
        return addLast(new DoublyLinkedListNode<>(val));
    }

    public DoublyLinkedListNode<E> getFirst() {
        if (isEmpty()) {
            return null;
        }
        return dummyHead.getNext();
    }

    public boolean isEmpty() {
        return dummyHead.getNext() == dummyTail;
    }
}
