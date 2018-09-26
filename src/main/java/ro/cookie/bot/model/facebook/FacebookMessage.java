package ro.cookie.bot.model.facebook;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FacebookMessage {
    private List<Entry> entries;

    @JsonCreator
    public FacebookMessage(
            @JsonProperty("entry")
            List<Entry> entries) {
        this.entries = entries;
    }


    public List<Entry> getEntries() {
        return new ArrayList<>(entries);
    }
}
