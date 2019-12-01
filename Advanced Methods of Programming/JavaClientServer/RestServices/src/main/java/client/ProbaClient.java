package client;

import chat.services.rest.Proba;
import chat.services.rest.ServiceException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;

public class ProbaClient {
    public static final String URL = "http://localhost:8080/gestiune/probe";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new ServiceException(e);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Proba[] getAll() {
        return execute(() -> restTemplate.getForObject(URL, Proba[].class));
    }

    public Proba getById(String id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Proba.class));
    }

    public Proba create(Proba proba) {
        return execute(() -> restTemplate.postForObject(URL, proba, Proba.class));
    }

    public void update(Proba proba) {
        execute(() -> {
            restTemplate.put(String.format("%s/%s", URL, proba.getId()), proba);
            return null;
        });
    }

    public void delete(String id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }
}
