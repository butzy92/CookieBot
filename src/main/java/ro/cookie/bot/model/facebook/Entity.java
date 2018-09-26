package ro.cookie.bot.model.facebook;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Entity {
    private BigDecimal confidence;
    private String responseText;

    public Entity(
            @JsonProperty("confidence")
            BigDecimal confidence,
            @JsonProperty("value")
            String responseText) {
        this.confidence = confidence;
        this.responseText = responseText;
    }

    public BigDecimal getConfidence() {
        return confidence;
    }

    public String getResponseText() {
        return responseText;
    }
}
