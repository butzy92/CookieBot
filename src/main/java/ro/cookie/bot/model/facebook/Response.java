package ro.cookie.bot.model.facebook;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    private final Map<String, List<Entity>> entities;

    @JsonCreator
    public Response(
            @JsonProperty("entities")
            Map<String, List<Entity>> entities) {
        this.entities = entities;
    }

    public Map<String, List<Entity>> getEntities() {
        return new HashMap<>(entities);
    }

    public Optional<Entity> getMaxHighConfidence(){
        return entities.values()
                .stream()
                .flatMap(List::stream)
                .max(Comparator.comparing(Entity::getConfidence));
    }
}
