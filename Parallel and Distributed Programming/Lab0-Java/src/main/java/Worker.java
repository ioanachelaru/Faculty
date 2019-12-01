import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Worker {

    private void createFile(String file_name){
        try {
            File my_file = new File(file_name);
            if (my_file.createNewFile()) {
                System.out.println("File created: " + my_file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFileEasy(String file_name, Integer size, Integer min, Integer max){
        createFile(file_name);

        try {
            FileWriter myWriter = new FileWriter(file_name);
            Random random = new Random();

            while(size>0){

                //Genereaza random numarul
                int random_integer = random.nextInt((max - min) + 1) + min;

                //String-ul in care va fi construit
                StringBuilder string = new StringBuilder();
                string.append(random_integer);

                size--;

                //Adauga separatori
                if(size>0)
                    string.append(", ");

                myWriter.write(string.toString());
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeToFile(String file_name, Integer size, Integer min, Integer max){

        createFile(file_name);

        try {
            FileWriter myWriter = new FileWriter(file_name);
            Random random = new Random();

            while(size>0){

                //Genereaza random numarul de cifre
                int random_size = random.nextInt((max - min) + 1) + min;

                StringBuilder string = new StringBuilder();

                //Genereaza prima cifra a numarului != 0
                if(random_size>0) {
                    string = new StringBuilder(String.valueOf(random.nextInt(10) + 1));
                    random_size--;
                }

                //Genereaza restul cifrelor
                while(random_size>0){
                    int random_integer = random.nextInt(10);
                    string.append(random_integer);
                    random_size--;
                }
                size--;

                //Adauga separatori
                if(size>0)
                    string.append(", ");

                myWriter.write(string.toString());
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean compareFiles(String first_file, String second_file) throws IOException {

        BufferedReader reader1 = new BufferedReader(new FileReader(first_file));
        BufferedReader reader2 = new BufferedReader(new FileReader(second_file));

        String line1 = reader1.readLine();
        String line2 = reader2.readLine();

        boolean areEqual = true;

        while (line1 != null || line2 != null)
        {
            if(line1 == null || line2 == null)
            {
                areEqual = false;
                break;
            }
            else if(! line1.equalsIgnoreCase(line2))
            {
                areEqual = false;
                break;
            }

            line1 = reader1.readLine();
            line2 = reader2.readLine();
        }

        reader1.close();
        reader2.close();

        return areEqual;
    }

    public void writeListInFile(List<Integer> list, String file_name) throws IOException {
        FileWriter fileWriter = new FileWriter(file_name, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        list.forEach(integer -> printWriter.print(integer + ","));
        printWriter.println();

        printWriter.close();

    }

    public List<Integer> getDataFromFile(String file_name){
        List<Integer> lst = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(new FileReader(file_name))){
            String line = bf.readLine();
            String[] numbers = line.split(", ");
            for (String n: numbers) {
                Integer nr = Integer.valueOf(n);
                lst.add(nr);
            }
        }
        catch (IOException ex) {
            System.out.println("Eroare de fisier\n");
        }
        return lst;
    }
}