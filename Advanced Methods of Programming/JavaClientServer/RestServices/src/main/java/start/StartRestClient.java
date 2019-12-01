package start;

import chat.services.rest.Proba;
import chat.services.rest.ServiceException;
import client.ProbaClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class StartRestClient {
    private final static ProbaClient probaClient = new ProbaClient();
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Proba userT = new Proba(107,50,"mixt");
        try{
            show(()-> System.out.println(probaClient.create(userT)));
            show(()->{
                Proba[] res = probaClient.getAll();
                for(Proba u:res){
                    System.out.println(u.getId()+": "+u.getDistanta()+": "+u.getStil());
                }
            });
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }

    }



    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            System.out.println("Service exception"+ e);
        }
    }
}
