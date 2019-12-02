package Varianta1B;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedLinkedList<T> {
    public Node head;
    public ReentrantLock lockList;

    public class Node {
        private T data;
        public Node next;
        public ReentrantLock lockNode;

        // Node constructor links the node as a new head
        Node(T data) {
            this.data = data;
            this.next = null;
            this.lockNode = new ReentrantLock();
        }

        public T getData(){ return this.data; }

        public void setData(T data){ this.data = data; }
    }

    // Constructor without parameters
    public SynchronizedLinkedList() {
        this.head = null;
        this.lockList = new ReentrantLock();
    }

    // Returns the node from a certain position
    public Node getNode(int index){
        int start = 0;

        this.head.lockNode.lock();
        Node start_node = this.head;
        this.head.lockNode.unlock();

        while( start != index && start <= index){
            start_node = start_node.next;
            start++;
        }
        if(start > index)
            throw new IndexOutOfBoundsException("Index out of bounds\n");
        return start_node;
    }

    public void setValueOfNode(int index, T value){
        int start = 0;

        this.head.lockNode.lock();
        Node start_node = this.head;
        this.head.lockNode.unlock();

        while( start != index && start <= index){
            start_node = start_node.next;
            start++;
        }
        if(start > index)
            throw new IndexOutOfBoundsException("Index out of bounds\n");
        else
        {
            Node nod = this.getNode(index);

            nod.lockNode.lock();
            nod.setData(value);
            nod.lockNode.unlock();
        }

    }

    // Returns the size of the linked list
    public int size() {
        Node temp = this.head;

        int count = 0;
        while (temp != null)
        {
            count++;
            temp = temp.next;
        }
        return count;
    }

    // Method to insert a new node to the end
    public void insert(T data) {

        // Create a new node with given data
        Node new_node = new Node(data);
        new_node.next = null;

        // If the Linked List is empty,
        // then make the new node as head
        if (this.head == null) {
            this.head = new_node;
        }
        else {
            // Else traverse till the last node
            // and insert the new_node there

            this.head.lockNode.lock();
            Node last = this.head;
            this.head.lockNode.unlock();

            while (last.next != null) {
                last.lockNode.lock();
                Node prev = last;
                last = last.next;
            }

            // Insert the new_node at last node
            last.lockNode.lock();
            last.next = new_node;
            last.lockNode.unlock();
        }
    }

    // Method to insert a new node in front
    public void insertFront(T data){
        // Create a new node with given data
        Node new_node = new Node(data);
        new_node.next = null;

        // If the Linked List is empty,
        // then make the new node as head
        if (this.head == null) {
            this.head = new_node;
        }
        else {
            // Else set the new node to be the head
            new_node.next = this.head;
            this.head = new_node;
        }
    }

    // Method to print the LinkedList.
    public void printList() {
        Node currNode = this.head;

        System.out.print("SyncronizedLinkedList: ");

        // Traverse through the LinkedList
        while (currNode != null) {
            // Print the data at current node
            System.out.print(currNode.data + " ");

            // Go to next node
            currNode = currNode.next;
        }
    }
}