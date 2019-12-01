import Data_structures.SynchronizedLinkedList;
import java.io.IOException;

public class Task2 {
    private SynchronizedLinkedList<Integer> poli_result;
    private SynchronizedLinkedList<Integer> poli_current;

    private Worker2 worker;

    private int number_threads;
    private int number_tasks;

    private String file_name;
    private String result_file;

    public Task2(int nth, int nta, String file_name, String result_file) {
        this.number_threads = nth;
        this.number_tasks = nta;

        this.file_name = file_name;
        this.result_file = result_file;

        this.poli_current = new SynchronizedLinkedList<>();
        this.poli_result = new SynchronizedLinkedList<>();

        this.worker = new Worker2();
    }

    public SynchronizedLinkedList<Integer> getResult(){ return this.poli_result; }

    private void writeFiles() throws IOException {
        for (int i = 1; i <= number_tasks; i++) {
            worker.writePoli(worker.generatePoli(),file_name+i+".txt");
        }
    }

    private void addByIndex(int index, int data){
        this.poli_result.setValueOfNode(index, data + this.poli_result.getNode(index).getData());
    }

    private void addPolynomials(){
        if (this.poli_result.size() == 0) {

            this.poli_result.lockList.lock();

            SynchronizedLinkedList.Node temp = this.poli_current.head;
            while (temp != null) {
                this.poli_result.insert((Integer) temp.getData());
                temp = temp.next;
            }

            this.poli_result.lockList.unlock();

        }
        else {
            this.poli_result.lockList.lock();

            while (this.poli_result.size() < poli_current.size()) {
                this.poli_result.insertFront(0);
            }

            while (this.poli_result.size() > poli_current.size()) {
                this.poli_current.insertFront(0);
            }
            this.poli_result.lockList.unlock();

            int i = 0;
            SynchronizedLinkedList.Node temp = poli_current.head;
            while (temp != null) {
                this.addByIndex(i, (Integer) temp.getData());
                temp = temp.next;
                i++;
            }
        }
    }

    public void run() throws IOException, InterruptedException {
        this.writeFiles();

        Thread[] threads = new Thread[this.number_threads];

        int start = 1, stop;
        int chunck_size = this.number_tasks / this.number_threads;
        int reminder = this.number_tasks % this.number_threads;

        for(int i = 0; i < this.number_threads; i++){
            if(reminder > 0) {
                stop = start + chunck_size + 1;
                reminder--;
            }
            else stop = start + chunck_size;

            final int end = stop, begin = start ;

            threads[i] = new Thread(()->{
                for (int j = begin; j < end; j++) {
                    this.poli_current = worker.readPoli(file_name + j + ".txt");
                    this.addPolynomials();
                }
            });

            start = stop;
        }

        for (int i = 0; i < this.number_threads; i++) {
            threads[i].start();
        }

        for(int i = 0; i < this.number_threads; i++){
            threads[i].join();
        }

        worker.writePoli(this.poli_result, result_file);
    }
}