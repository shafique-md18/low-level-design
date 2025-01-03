package parkinglot.model.parking;

import java.util.UUID;

public abstract class Gate {
    private String id;
    private String name;

    public Gate(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Gate(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
