package Varianta2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Task_v2 {
    private SynchronizedQueue queue;
    private SortedLinkedList poli_result;

    private Worker worker;

    private int number_threads;
    private int number_tasks;

    private String file_name;
    private String result_file;

    private static final int end_of_input = 0xdeadbeef;

    public Task_v2(int nth, int nta, String file_name, String result_file) {
        this.number_threads = nth;
        this.number_tasks = nta;
        this.file_name = file_name;
        this.result_file = result_file;
        this.poli_result = new SortedLinkedList();
        this.queue = new SynchronizedQueue();
        this.worker = new Worker();
    }

    private void writeFiles() throws IOException {
        for (int i = 1; i <= this.number_tasks; i++) {
            worker.writePoli(worker.generatePoli(), file_name + i + ".txt");
        }
    }

    public synchronized void run() throws IOException, InterruptedException {
        this.writeFiles();

        Thread main_thread = new Thread(() -> {
            for (int i = 1; i <= this.number_tasks; i++) {
                try(BufferedReader bf = new BufferedReader(new FileReader(file_name + i + ".txt"))){
                    String line;
                    while ((line = bf.readLine()) != null) {
                        String[] numbers = line.split(" ");
                        int coef = Integer.parseInt(numbers[0]);
                        int rank = Integer.parseInt(numbers[1]);

                        this.queue.push(new Pair(coef, rank));
                    }
                }
                catch (IOException ex) {
                    System.out.println("Eroare de fisier\n");
                }
            }
            // Mark the end
            this.queue.push(new Pair(end_of_input,1));
        });

        main_thread.start();

        ExecutorService executorService = Executors.newFixedThreadPool(this.number_threads);
        AtomicBoolean done = new AtomicBoolean(false);
        for (int i = 0; i < this.number_threads; i++) {
            executorService.submit(() -> {
                while (true) {
                    Pair pair = this.queue.pop();

                    if (pair.coeficient == end_of_input) {
                        done.set(true);
                        notify();
                        break;
                    }

                    this.poli_result.sortedInsert(this.poli_result.newNode(pair));
                }
            });
        }
        if (!done.get()) {
            try {
                wait();
                executorService.shutdownNow();
            } catch (InterruptedException e) {
                return;
            }
        }
        else
            executorService.shutdownNow();

        main_thread.join();

        this.worker.writePoli(this.poli_result, this.result_file);
    }
}
