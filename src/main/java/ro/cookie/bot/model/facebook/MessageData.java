package ro.cookie.bot.model.facebook;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageData {
    private Message message;
    private final Sender sender;

    @JsonCreator
    public MessageData(
            @JsonProperty("message")
                    Message message,
            @JsonProperty("sender")
            Sender sender) {
        this.message = message;
        this.sender = sender;
    }

    public Message getMessage() {
        return message;
    }


    public String getUserId(){
        return sender.getId();
    }

    static class Sender {
        private final String id;

        @JsonCreator
        Sender(
                @JsonProperty("id")
                        String id) {
            this.id = id;
        }

        String getId() {
            return id;
        }
    }
}
