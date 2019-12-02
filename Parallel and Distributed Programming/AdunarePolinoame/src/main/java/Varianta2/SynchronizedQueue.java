package Varianta2;
import java.util.LinkedList;

public class SynchronizedQueue {
    private LinkedList<Pair> queue;

    public SynchronizedQueue(){ this.queue = new LinkedList<>(); }

    public synchronized Pair pop() {
        while (this.queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }
        return this.queue.poll();
    }

    public synchronized void push(Pair pair){
        this.queue.add(pair);
        notify();
    }
}
