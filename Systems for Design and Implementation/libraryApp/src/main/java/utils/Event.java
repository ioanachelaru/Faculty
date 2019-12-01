package utils;

public class Event<E> {
    private EventType type;
    private E data;
    private E oldata;

    public Event(EventType type, E data) {
        this.type = type;
        this.data = data;
    }

    public Event(EventType type, E data, E oldata) {
        this.type = type;
        this.data = data;
        this.oldata = oldata;
    }

    public EventType getType() {
        return type;
    }

    public E getData() {
        return data;
    }

    public E getOldata() {
        return oldata;
    }
}

