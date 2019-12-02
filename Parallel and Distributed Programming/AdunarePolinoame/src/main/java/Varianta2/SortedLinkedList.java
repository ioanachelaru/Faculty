package Varianta2;

public class SortedLinkedList {
    Node head;

    public class Node{
        public Pair data;
        public Node next;

        public Node(Pair d) {
            data = d;
            next = null;
        }
    }

    public SortedLinkedList(){ this.head = null; }

    /* Function to insert a new_node in a list. */
    public void sortedInsert(Node new_node)
    {
        Node current;

        synchronized (this) {

            /* Special case for head node */
            if (head == null || this.head.data.rank < new_node.data.rank) {
                new_node.next = this.head;
                this.head = new_node;
            } else if (this.head.data.rank == new_node.data.rank)
                this.head.data.coeficient += new_node.data.coeficient;
            else {

                /* Locate the node before point of insertion. */
                current = this.head;

                while (current.next != null &&
                        current.next.data.rank > new_node.data.rank)
                    current = current.next;

                if (current.next != null && current.next.data.rank == new_node.data.rank) {
                    current = current.next;
                    current.data.coeficient += new_node.data.coeficient;
                } else {
                    new_node.next = current.next;
                    current.next = new_node;
                }
            }
        }
    }

    /* Function to create a node */
    public Node newNode(Pair data)
    {
        Node x = new Node(data);
        return x;
    }
}
