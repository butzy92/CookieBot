package ro.cookie.bot.model.facebook;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Entry {

    private List<MessageData> messageDataList;

    @JsonCreator
    public Entry(
            @JsonProperty("messaging")
                    List<MessageData> messageDataList) {
        this.messageDataList = messageDataList;
    }

    public List<MessageData> getMessageDataList() {
        return new ArrayList<>(messageDataList);
    }
}
