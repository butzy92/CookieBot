package ro.cookie.bot.model.facebook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

    private String text;
    private Response response;


    @JsonCreator
    public Message(
            @JsonProperty("text")
                    String text,
            @JsonProperty("nlp")
                    Response response) {
        this.text = text;
        this.response = response;
    }

    public String getText() {
        return text;
    }

    public Response getResponse() {
        return response;
    }

}
