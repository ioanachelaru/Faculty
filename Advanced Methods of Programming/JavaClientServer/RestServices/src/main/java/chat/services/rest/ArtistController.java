package chat.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/gestiune/probe")
public class ArtistController {

    private static final String template = "Hello, %s!";

    @Autowired
    private ProbaRepository probaRepository;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return String.format(template, name);
    }

    @RequestMapping( method= RequestMethod.GET)
    public Iterable<Proba> getAll(){
        return probaRepository.getProbe();
    }

    @RequestMapping(value="/{username}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String username){
        System.out.println("Deleting user ... "+username);
        try {
            probaRepository.delete(Integer.parseInt(username));
            return new ResponseEntity<Proba>(HttpStatus.OK);
        }catch (Exception ex){
            System.out.println("Ctrl Delete user exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public Proba create(@RequestBody Proba artist){
        probaRepository.save(artist);
        return artist;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Proba update(@RequestBody Proba artist) {
        System.out.println("Updating user ...");
        probaRepository.update(artist.getId(),artist);
        return artist;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id){

        Proba user = probaRepository.find(Integer.parseInt(id));
        if (user==null)
            return new ResponseEntity<String>("User not found",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Proba>(user, HttpStatus.OK);
    }
}
