import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Clasa ce retine informatiile despre un automat finit
 * @numeFisier - path-ul catre fisierul de referinta
 * @automat - lista de stari
 */
public class AutomatFinit {

    private String numeFisier;
    private List<State> automat;

    public AutomatFinit(String numeFisier) {
        this.numeFisier = numeFisier;
        this.getAutomatFinit();
    }

    /**
     * @return multimea de stari ale automatului
     */
    public List<String> getMultimeStari(){
        List<String> stari = new ArrayList<>();

        for(State state : this.automat){
            stari.add(state.getDescription());
        }

        return stari;
    }

    /**
     * @return alfabetul folosit de automatul finit
     */
    public Set<String> getAlfabet(){
        Set<String> alfabet = new HashSet<>();

        for (State state : automat){
            if(state.getDestinations().size() > 0){
                alfabet.addAll(state.getDestinations().keySet());
            }
        }

        return alfabet;
    }

    /**
     * @return multimea starilor finale ale automatului finit
     */
    public List<String> getMultimeStariFinale(){
        List<String> stariFinale = new ArrayList<>();

        for (State state : automat){
            if (state.isFinalState())
                stariFinale.add(state.getDescription());
        }

        return stariFinale;
    }

    /**
     * @return automatul finit
     */
    public List<State> getAutomat() {
        return automat;
    }

    /**
     * @return starea initiala a automatului
     */
    private State getStareInitiala(){
        return this.automat.get(0);
    }

    /**
     * Functia populeaza lista automat cu datele din fisierul de referinta
     */
    private void getAutomatFinit() {
        this.automat = new ArrayList<>();

        try(BufferedReader bf = new BufferedReader(new FileReader(this.numeFisier))){
            String line;

            while ((line = bf.readLine()) != null){
                String[] el = line.split(",");
                if (el.length == 2){
                    if(el[1].equals("initial")){

                        State state = new State(el[0]);
                        state.setInitialState(true);
                        this.automat.add(state);

                    }else if(el[1].equals("final")){

                        State state = new State(el[0]);
                        state.setFinalState(true);
                        this.automat.add(state);
                    }
                }
                if(el.length == 3){
                    boolean gasit = false;
                    for(int i = 0; i < this.automat.size(); i++){
                        if (this.automat.get(i).getDescription().equals(el[0])){
                        this.automat.get(i).addDestination(el[1],new State(el[2]));
                            gasit = true;
                        }
                    }
                    if(!gasit){
                        State state = new State(el[0]);
                        state.addDestination(el[1], new State(el[2]));
                        this.automat.add(state);
                    }
                }
            }
        }
        catch (IOException ex) {
            System.out.println("Eroare de fisier\n");
        }

}

    /**
     * Verifica daca o secventa data este acceptata sau nu
     * @param sequence -  String
     * @return boolean
     */
    public boolean checkSequence(String sequence) {
        String[] el = sequence.split(",");

        List<String> elementsOfSequence = new ArrayList<>(Arrays.asList(el));

        for(int i=0;i < elementsOfSequence.size(); i++){
            if(!this.getAlfabet().contains(elementsOfSequence.get(i))){
                return false;
            }
        }

        int i = 0;
        State currentState = this.getStareInitiala();
        while(elementsOfSequence.size() != 0){
            String element = elementsOfSequence.get(i);
            if(currentState.getDestinations().get(element) != null){
                currentState = this.getAState(currentState.getDestinations().get(element).get(0).getDescription());
                elementsOfSequence.remove(i);
                i--;
            }
            else return false;
            i++;
        }

        if(currentState.isFinalState())
            return true;
        else return false;
    }

    /**
     * Returneaza starea unui nod din automat
     * @param description String
     * @return State of description
     */
    private State getAState(String description){
        for (State state : this.automat){
            if (state.getDescription().equals(description))
                return state;
        }
        return null;
    }

    /**
     * Returneaza cel mai lung prefix al unei secvente date
     * @param sequence - String
     * @return String
     */
    public String getLongestPrefix(String sequence) {
        String[] el = sequence.split(",");

        List<String> elementsOfSequence = new ArrayList<>(Arrays.asList(el));

        String longestPrefix = "";

        int i = 0;
        State currentState = this.getStareInitiala();
        while(elementsOfSequence.size() != 0){
            String element = elementsOfSequence.get(i);
            if(currentState.getDestinations().get(element) != null){
                currentState = this.getAState(currentState.getDestinations().get(element).get(0).getDescription());

                longestPrefix = longestPrefix + elementsOfSequence.get(i);

                elementsOfSequence.remove(i);
                i--;
            }
            else if(currentState.isFinalState()){
                return longestPrefix;
            }
            i++;
        }

        if(currentState.isFinalState()){
            longestPrefix = longestPrefix.substring(0, longestPrefix.length() - 1);
            return longestPrefix;
        }
        else{
            longestPrefix = longestPrefix.substring(0, longestPrefix.length() - 1);
            return longestPrefix;
        }
    }
}
