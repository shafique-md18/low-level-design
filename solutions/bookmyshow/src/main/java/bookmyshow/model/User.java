package bookmyshow.model;

public class User {
    private final String id;
    private final String name;
    private final String email;

    public User(String email, String id, String name) {
        this.email = email;
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
