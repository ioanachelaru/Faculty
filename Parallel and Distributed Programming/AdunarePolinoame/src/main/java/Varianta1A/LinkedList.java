package Varianta1A;

public class LinkedList<T> {
    public Node head;

    public class Node {
        private T data;
        public Node next;

        // Node constructor links the node as a new head
        Node(T data) {
            this.data = data;
            this.next = null;
        }

        public T getData(){ return this.data; }

        public void setData(T data){ this.data = data; }
    }

    // Constructor without parameters
    public LinkedList() { this.head = null; }

    // Returns the node from a certain position
    public Node getNode(int index){
        int start = 0;
        Node start_node = this.head;

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
        Node start_node = this.head;

        while( start != index && start <= index){
            start_node = start_node.next;
            start++;
        }
        if(start > index)
            throw new IndexOutOfBoundsException("Index out of bounds\n");
        else this.getNode(index).setData(value);
    }

    // Returns the size of the linked list
    public int size() {
        Node temp = head;
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
            Node last = this.head;
            while (last.next != null) {
                last = last.next;
            }

            // Insert the new_node at last node
            last.next = new_node;
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

        System.out.print("LinkedList: ");

        // Traverse through the LinkedList
        while (currNode != null) {
            // Print the data at current node
            System.out.print(currNode.data + " ");

            // Go to next node
            currNode = currNode.next;
        }
    }
}