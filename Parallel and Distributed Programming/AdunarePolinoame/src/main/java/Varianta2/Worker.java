package Varianta2;
import java.io.*;
import java.util.Random;

public class Worker {

    private void createFile(String file_name){
        try {
            File my_file = new File(file_name);
            if (my_file.createNewFile()) {
                System.out.println("File created: " + my_file.getName());
            } else {
                System.out.println("File already exists.");

                // Empty file
                PrintWriter writer = new PrintWriter(file_name);
                writer.print("");
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public SortedLinkedList readPoli(String file_name){
        SortedLinkedList lst = new SortedLinkedList();

        try(BufferedReader bf = new BufferedReader(new FileReader(file_name))){
            String line;
            while ((line = bf.readLine()) != null) {
                String[] numbers = line.split(" ");
                int coef = Integer.parseInt(numbers[0]);
                int rank = Integer.parseInt(numbers[1]);

                SortedLinkedList.Node node = lst.newNode(new Pair(coef, rank));
                lst.sortedInsert(node);
            }
        }
        catch (IOException ex) {
            System.out.println("Eroare de fisier\n");
        }
        return lst;
    }

    public void writePoli(SortedLinkedList list, String file_name) throws IOException {
        this.createFile(file_name);

        FileWriter fileWriter = new FileWriter(file_name, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        String string;

        SortedLinkedList.Node node = list.head;
        while(node != null){
            string = node.data.coeficient + " " + node.data.rank;
            printWriter.println(string);
            node = node.next;
        }
        printWriter.close();
    }

    public SortedLinkedList generatePoli(){
        int max_size = 20, min_size = 0;
        Random r = new Random();
        int size = r.nextInt((max_size - min_size) + 1) + min_size;

        SortedLinkedList poli = new SortedLinkedList();

        int max = 100, min = 0;

        for (int i = 0; i <= size; i++) {
            int coef = r.nextInt((max - min) + 1) + min;
            int rank = r.nextInt((max - min) + 1) + min;
            SortedLinkedList.Node node = poli.newNode(new Pair(coef,rank));
            poli.sortedInsert(node);
        }

        return poli;
    }
}