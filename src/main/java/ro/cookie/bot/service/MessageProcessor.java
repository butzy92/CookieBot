package ro.cookie.bot.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ro.cookie.bot.model.facebook.Entity;
import ro.cookie.bot.model.facebook.Entry;
import ro.cookie.bot.model.facebook.FacebookMessage;
import ro.cookie.bot.model.facebook.MessageData;

@Service
public class MessageProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

    private final ObjectMapper objectMapper;
    private final List<String> predefinedMessages;
    private final MessageSender messageSender;
    private final BigDecimal maxConfidence;

    public MessageProcessor(MessageSender messageSender,
                            @Value("${witAi.confidence}")
                            BigDecimal maxConfidence) {
        this.messageSender = messageSender;
        this.maxConfidence = maxConfidence;
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        predefinedMessages = new ArrayList<>();
        predefinedMessages.add("Ham Ham! Incearca cu intrebari mai usoare");
        predefinedMessages.add("No daca-i musai ham!");
    }

    @Async
    public void processMessage(String message){
        try {
            FacebookMessage facebookMessage = objectMapper.readValue(message, FacebookMessage.class);

            facebookMessage.getEntries().forEach(this::processEntry);
        } catch (IOException e) {
            LOGGER.error("error on parsing body", e);
        }
    }

    private void processEntry(Entry entry) {
        entry.getMessageDataList().forEach(this::processMessageData);
    }

    private void processMessageData(MessageData messageData) {
        String userId = messageData.getUserId();
        messageSender.sendTypingOn(userId);

        String response = messageData.getMessage()
                .getResponse()
                .getMaxHighConfidence()
                .filter(confidence -> confidence.getConfidence().compareTo(maxConfidence) > 0)
                .map(Entity::getResponseText)
                .orElseGet(() -> predefinedMessages.stream().sorted().findFirst().orElse("Ham"));

        messageSender.sendMessage(userId, response);
    }
}
