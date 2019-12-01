import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Meniu {

    private final AutomatFinit automatFinit;

    public Meniu(AutomatFinit automatFinit) {
        this.automatFinit = automatFinit;
    }

    public void run(){
        Scanner keyboard = new Scanner(System.in);
        while (true){
            this.showOptions();
            String comand = keyboard.next();
            if (comand.equals("1")){
                this.showMultimeaStarilor();
            }
            
            else if (comand.equals("2")){
                this.showAlfabet();
            }
            
            else if (comand.equals("3")){
                this.showTranzitii();
            }

            else if (comand.equals("4")){
                this.showMultimeaStarilorFinale();
            }

            else if (comand.equals("5")){
                this.checkSequence();
            }

            else if(comand.equals("6")){
                this.getLongestPrefix();
            }

            else if (comand.equals("0")){
                break;
            }
            else {
                System.out.println("You failed us");
                System.out.println();
            }

        }
    }

    private void getLongestPrefix() {
        System.out.println("Introdu secventa: ");
        Scanner keyboard = new Scanner(System.in);
        String sequence = keyboard.nextLine();

        System.out.println("Cel mai lung prefix al secventei este: ");
        String longestSequence = this.automatFinit.getLongestPrefix(sequence);
        System.out.println(longestSequence);

        System.out.println();
        System.out.println("_________________________________________");
    }

    private void checkSequence() {
        System.out.println("Introdu secventa: ");
        Scanner keyboard = new Scanner(System.in);
        String sequence = keyboard.nextLine();

        if(this.automatFinit.checkSequence(sequence)){

            System.out.print("Secventa acceptata! ");

        }else System.out.print("Secventa neacceptata! ");

        System.out.println();
        System.out.println("_________________________________________");
    }

    private void showMultimeaStarilorFinale() {
        System.out.println("MULTIMEA STARILOR FINALE ESTE: ");
        for(String stare : automatFinit.getMultimeStariFinale())
            System.out.print(stare + " ");
        System.out.println();
        System.out.println("_________________________________________");
    }

    private void showTranzitii() {

        List<State> automat = this.automatFinit.getAutomat();

        for(State state : automat){
            String initialState = state.getDescription();
            if(state.getDestinations().size() > 0){
                Set<String> lettersOfInitialState = state.getDestinations().keySet();
                for(String letter : lettersOfInitialState){
                    for (State state1 : state.getDestinations().get(letter)){
                        System.out.println(initialState + " -> " + letter + " -> " + state1.getDescription());
                    }
                }
            }
        }

    }

    private void showAlfabet() {
        System.out.println("ALFABETUL ESTE: ");
        for(String litera : automatFinit.getAlfabet())
            System.out.print(litera + " ");
        System.out.println();
        System.out.println("_________________________________________");
    }

    private void showMultimeaStarilor() {
        System.out.println("MULTIMEA STARILOR ESTE: ");
        for(String stare : automatFinit.getMultimeStari())
            System.out.print(stare + " ");
        System.out.println();
        System.out.println("_________________________________________");
    }

    private void showOptions() {
        System.out.println("0. Break");
        System.out.println("1. Multimea starilor");
        System.out.println("2. Alfabetul");
        System.out.println("3. Tranzitiile");
        System.out.println("4. Multimea starilor finale");
        System.out.println("5. Verifica secventa");
        System.out.println("6. Cel mai lung prefix");
        System.out.println("_________________________________________");
    }

}
