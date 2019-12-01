package service;

import domain.Participant;
import repo.RepoException;
import repo.RepoParticipant;
import validatori.ValidationException;
import validatori.ValidatorParticipant;

public class ServiceParticipant {
    private RepoParticipant repoParticipant;
    private ValidatorParticipant validatorParticipant;

    public ServiceParticipant(RepoParticipant repoParticipant,ValidatorParticipant validatorParticipant){
        this.repoParticipant=repoParticipant;
        this.validatorParticipant=validatorParticipant;
    }

    public void save(Participant object) throws ValidationException {
        Participant id=this.repoParticipant.findOne(object.getId());
        if(id==null)
            try {
                this.validatorParticipant.validate(object);
                this.repoParticipant.save(object);
            } catch (ValidationException e) {
                throw new ValidationException(e.getMessage());
            }
        else throw new RepoException("Participantul exista deja");
    }

    public Iterable<Participant> findAll() { return this.repoParticipant.findAll(); }

    public Participant findOne(Integer integer) {
        Participant participant=this.findOne(integer);
        if(participant==null)
            throw new RepoException("Participant inexistent!");
        else return participant;
    }

    public int size(){ return this.repoParticipant.size(); }

    public Participant findByNameAndAge(String name, int age){
        Participant participant=this.findByNameAndAge(name,age);
        if(participant==null)
            throw new RepoException("Participant inexistent!");
        else return participant;
    }
}
