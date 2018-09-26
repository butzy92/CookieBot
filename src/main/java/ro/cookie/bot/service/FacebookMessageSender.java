package ro.cookie.bot.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import ro.cookie.bot.controller.FacebookWebhook;

@Service
public class FacebookMessageSender implements MessageSender {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookWebhook.class);

    private final RestTemplate restTemplate;
    private final FacebookApi facebookApi;

    public FacebookMessageSender(RestTemplate restTemplate, FacebookApi facebookApi) {
        this.restTemplate = restTemplate;
        this.facebookApi = facebookApi;
    }

    @Override
    public void sendMessage(String userId, String response) {
        try {
            restTemplate.postForObject(facebookApi.getApiForSendMessage(),
                    MessageForSend.getMessageForResponse(userId, response)
                    , String.class);
        } catch (RestClientResponseException e) {
            LOGGER.error("Error: ", e);
            LOGGER.info("Response: {}", e.getResponseBodyAsString());
        }
    }

    @Override
    public void sendTypingOn(String userId) {
        try {
            restTemplate.postForObject(facebookApi.getApiForSendMessage(),
                    MessageForSend.getMessageForTypingOn(userId)
                    , String.class);
        } catch (RestClientResponseException e) {
            LOGGER.error("Error: ", e);
            LOGGER.info("Response: {}", e.getResponseBodyAsString());
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class MessageForSend {
        private String messagingType;
        private String userId;
        private String message;
        private String action;

        MessageForSend(String messagingType, String userId, String message, String action) {
            this.messagingType = messagingType;
            this.userId = userId;
            this.message = message;
            this.action = action;
        }

        static MessageForSend getMessageForResponse(String userId, String message){
            return new MessageForSend("RESPONSE", userId, message, null);
        }

        static MessageForSend getMessageForTypingOn(String userId){
            return new MessageForSend("RESPONSE", userId, null, "typing_on");
        }

        @JsonProperty("messaging_type")
        public String getMessagingType() {
            return messagingType;
        }

        @JsonProperty("recipient")
        public Recipient getUserId() {
            return new Recipient(userId);
        }

        @JsonProperty("message")
        public Message getMessage() {
            return message == null ? null : new Message(message);
        }

        @JsonProperty("sender_action")
        public String getAction() {
            return action;
        }

        static class Recipient {
            private final String id;

            Recipient(String id) {
                this.id = id;
            }

            public String getId() {
                return id;
            }
        }

        static class Message {
            private final String text;

            public Message(String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }
        }
    }

}
