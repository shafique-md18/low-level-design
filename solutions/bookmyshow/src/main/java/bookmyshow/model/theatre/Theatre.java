package bookmyshow.model.theatre;

import bookmyshow.model.City;

import java.util.List;
import java.util.Map;

public class Theatre {
    private final String id;
    private final Map<String, Screen> screens;
    private final City city;
    private final Map<String, Show> shows;

    public Theatre(String id, Map<String, Screen> screens, City city, Map<String, Show>  shows) {
        this.id = id;
        this.screens = screens;
        this.city = city;
        this.shows = shows;
    }

    public String getId() {
        return id;
    }

    public void addShow(Show show) {
        shows.put(show.getId(), show);
    }
}
