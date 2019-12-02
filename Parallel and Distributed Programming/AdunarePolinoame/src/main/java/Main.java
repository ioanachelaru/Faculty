import Varianta1A.Task;
import Varianta1B.Task2;
import Varianta2.SortedLinkedList;
import Varianta2.Task_v2;
import Varianta2.Worker;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        int number_threads = 5, number_tasks = 4;
        String file_name = "./src/main/resources/poli";
        String result_file = "./src/main/resources/result.txt";

//        long timeStart = System.nanoTime();
//        Task task = new Task(number_threads, number_tasks, file_name, result_file);
//        task.run();
//        long timeStop = System.nanoTime();
//
//        long timeStart = System.nanoTime();
//        Task2 task2 = new Task2(number_threads, number_tasks, file_name, result_file);
//        task2.run();
//        long timeStop = System.nanoTime();

        long timeStart = System.nanoTime();
        Task_v2 task = new Task_v2(number_threads, number_tasks, file_name, result_file);
        task.run();
        long timeStop = System.nanoTime();

        System.out.println(timeStop-timeStart);

    }
}