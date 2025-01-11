package bookmyshow.model;

public class Movie {
    private final String id;
    private final String name;
    private final Language language;
    private final long durationInMinutes;

    public Movie(String id, String name, Language language, long durationInMinutes) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.durationInMinutes = durationInMinutes;
    }

    public String getId() {
        return id;
    }

    public long getDurationInMinutes() {
        return durationInMinutes;
    }
}
