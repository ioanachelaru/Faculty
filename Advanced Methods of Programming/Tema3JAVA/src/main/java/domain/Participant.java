package domain;

public class Participant implements HasId<Integer> {
    private Integer idParticipant;
    private String numeParticipant;
    private Integer varstaParticipant;

    public Participant(Integer idParticipant,String numeParticipant,Integer varstaParticipant){
        this.idParticipant=idParticipant;
        this.numeParticipant=numeParticipant;
        this.varstaParticipant=varstaParticipant;
    }

    @Override
    public Integer getId() { return this.idParticipant; }

    @Override
    public void setId(Integer integer) { this.idParticipant=integer; }

    public String getNumeParticipant() { return numeParticipant; }

    public void setNumeParticipant(String numeParticipant) { this.numeParticipant = numeParticipant; }

    public Integer getVarstaParticipant() { return varstaParticipant; }

    public void setVarstaParticipant(Integer varstaParticipant) { this.varstaParticipant = varstaParticipant; }

    public String toString(){ return idParticipant+" "+numeParticipant+" "+varstaParticipant; }
}