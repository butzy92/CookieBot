package ro.cookie.bot.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class FacebookMessageSenderTest {

    @Test
    public void shouldSerializeMessageForSendCorrectly() throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(FacebookMessageSender.MessageForSend.getMessageForResponse("test", "test"));

        assertThat(json).isEqualTo("{\"messaging_type\":\"RESPONSE\",\"recipient\":{\"id\":\"test\"},\"message\":{\"text\":\"test\"}}");
    }

    @Test
    public void shouldSerializeMessageForTypingOnCorrectly() throws JsonProcessingException {
        String json = new ObjectMapper().writeValueAsString(FacebookMessageSender.MessageForSend.getMessageForTypingOn("test"));

        assertThat(json).isEqualTo("{\"messaging_type\":\"RESPONSE\",\"recipient\":{\"id\":\"test\"},\"sender_action\":\"typing_on\"}");
    }
}