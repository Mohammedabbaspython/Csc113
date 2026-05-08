import java.io.Serializable;

public class LinkedList implements Serializable {
    private Node head;
    private Node tail;
    private String name;

    public LinkedList() {
        this("No Name");
    }

    public LinkedList(String name) {
        head = null;
        tail = null;
        this.name = name;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void insertAtFront(Object data) {
        Node newNode = new Node(data);
        newNode.setNext(head);

        head = newNode;

        if (isEmpty()) {
            tail = newNode;
        }
    }

    public void insertAtBack(Object data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }

        tail = newNode;

    }

    public Object removeFromFront() {
        if (isEmpty()) {
            return null;
        }

        Node first = head;

        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.getNext();
        }

        return first.getData();

    }

    public Object removeFromBack() {
        if (isEmpty()) {
            return null;
        }

        Node current = head;


        if (head == tail) {
            head = null;
            tail = null;
        } else {
            Node previous = head;
            
            while (current.getNext() != null) {
                previous = current;
                current = current.getNext();
            }

            previous.setNext(null);
            tail = previous;
        }

        return current.getData();
    }

    public boolean remove(Object data) {
        if (isEmpty()) {
            return false;
        }

        if (head.getData().equals(data)) {
            removeFromFront();
            return true;
        }

        Node current = head.getNext();
        Node previous = head;

        while (current != null) {
            if (current.getData().equals(data)) {
                if (current == tail) {
                    removeFromBack();
                } else {
                    previous.setNext(current.getNext());
                }
                return true;
            }
            previous = current;
            current = current.getNext();
        }

        return false;
    }
    
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        int count = 0;
        Node current = head;

        while (current != null) {
            current = current.getNext();
            count++;
        }

        return count;
    
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }
    
    public String getName() {
        return name;
    }

}
