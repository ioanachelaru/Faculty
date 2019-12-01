import Data_structures.LinkedList;
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

    public LinkedList<Integer> readPoli(String file_name){
        LinkedList<Integer> lst = new LinkedList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader(file_name))){
            String line = bf.readLine();
            String[] numbers = line.split(",");

            int rank;
            if(numbers[0].contains("^")){
                String[] firstNum = numbers[0].split("\\^");
                rank = Integer.parseInt(firstNum[1]);
            }
            else if(numbers[0].contains("x"))
                rank = 1;
            else rank = 0;

            boolean added;
            for (String n: numbers) {
                added = false;
                if(rank > 1){

                    if(n.contains("^")){
                        String[] elements = n.split("\\^");

                        while(Integer.parseInt(elements[1]) != rank && rank > 1) {
                            lst.insert(0);
                            rank--;
                        }

                        if(Integer.parseInt(elements[1]) == rank) {

                            if(elements[0].equals("x"))
                                lst.insert(1);
                            else if(elements[0].equals("-x"))
                                lst.insert(-1);
                            else {
                                String[] elements2 = n.split("x");
                                lst.insert(Integer.valueOf(elements2[0]));
                            }
                            added = true;
                            rank--;
                        }
                    }
                }
                if(rank == 1 && !added){
                    if(n.contains("x")){

                        if(n.equals("x"))
                            lst.insert(1);
                        else if(n.equals("-x"))
                            lst.insert(-1);
                        else {
                            String[] elements = n.split("x");
                            lst.insert(Integer.valueOf(elements[0]));
                        }
                        added = true;
                    }
                    else lst.insert(0);

                    rank--;
                }
                if(rank == 0 && !added) {
                    lst.insert(Integer.valueOf(n));

                    added = true;
                    rank--;
                }
            }
            while (rank >= 0){
                lst.insert(0);
                rank--;
            }
        }
        catch (IOException ex) {
            System.out.println("Eroare de fisier\n");
        }
        return lst;
    }

    public void writePoli(LinkedList<Integer> list, String file_name) throws IOException {
        this.createFile(file_name);

        FileWriter fileWriter = new FileWriter(file_name, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        int rank = list.size() - 1, flag = 0;
        for (int i = 0; i < list.size(); i++) {
            int element = list.getNode(i).getData();

            StringBuilder stringBuilder = new StringBuilder();
            if(element != 0){
                if(rank > 1)
                    if(element != 1 || element != -1)
                        if(element < 0)
                            if(flag == 0)
                                stringBuilder.append(element).append("x^").append(rank);
                            else stringBuilder.append(",").append(element).append("x^").append(rank);
                        else
                            if(flag == 0)
                                stringBuilder.append(element).append("x^").append(rank);
                            else stringBuilder.append(",+").append(element).append("x^").append(rank);
                    else if(element == 1)
                            if(flag == 0)
                                stringBuilder.append("x^").append(rank);
                            else stringBuilder.append(",+x^").append(rank);
                        else
                            if(flag == 0)
                                stringBuilder.append("-x^").append(rank);
                            else stringBuilder.append(",-x^").append(rank);

                else if(rank == 1)
                    if(element != 1 || element != -1)
                        if(element < 0)
                            if(flag == 0)
                                stringBuilder.append(",").append(element).append("x");
                            else stringBuilder.append(element).append("x");
                        else
                            if(flag == 0)
                                stringBuilder.append(element).append("x");
                            else stringBuilder.append(",+").append(element).append("x");
                    else
                        if(element == 1)
                            if(flag == 0)
                                stringBuilder.append("x");
                            else stringBuilder.append(",+x");
                        else
                            if( flag == 0)
                                stringBuilder.append("-x");
                            else stringBuilder.append(",-x");
                else
                    if(element > 0)
                        if(flag == 0)
                            stringBuilder.append(element);
                        else stringBuilder.append(",+").append(element);
                    else
                        if(flag == 0)
                            stringBuilder.append(element);
                        else stringBuilder.append(",").append(element);
            flag = 1;
            }
            printWriter.print(stringBuilder);
            rank--;
        }
        if(flag == 0)
            printWriter.print(0);
        printWriter.println();
        printWriter.close();
    }

    public LinkedList<Integer> generatePoli(){
        int max = 20, min = 1;

        Random r = new Random();
        int size = r.nextInt((max - min) + 1) + min;

        LinkedList<Integer> poli = new LinkedList<>();

        Random rr = new Random();
        int maxx = 100, minn = 0;

        for (int i = 0; i < size; i++) {
            poli.insert(rr.nextInt((maxx - minn) + 1) + minn);
        }

        return poli;
    }
}