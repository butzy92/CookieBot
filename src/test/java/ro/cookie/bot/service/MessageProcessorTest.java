package ro.cookie.bot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import ro.cookie.bot.model.facebook.FacebookMessage;

public class MessageProcessorTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Test
    public void shouldMapCorrectly() throws IOException {
        String json = "{\n" +
                "\t\"object\": \"page\",\n" +
                "\t\"entry\": [{\n" +
                "\t\t\"id\": \"248239605890801\",\n" +
                "\t\t\"time\": 1537878291605,\n" +
                "\t\t\"messaging\": [{\n" +
                "\t\t\t\"sender\": {\n" +
                "\t\t\t\t\"id\": \"1514469385321837\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"recipient\": {\n" +
                "\t\t\t\t\"id\": \"248239605890801\"\n" +
                "\t\t\t},\n" +
                "\t\t\t\"timestamp\": 1537878290178,\n" +
                "\t\t\t\"message\": {\n" +
                "\t\t\t\t\"mid\": \"MurNEAnW1Vbzdh51mEIBf0HYAlKWKa-oC4-1KW5QDaEx6k0BrLctB0YSFNXgkukunDASb81pLLIpiKqRy_Oqdg\",\n" +
                "\t\t\t\t\"seq\": 1214555,\n" +
                "\t\t\t\t\"text\": \"salut\",\n" +
                "\t\t\t\t\"nlp\": {\n" +
                "\t\t\t\t\t\"entities\": {\n" +
                "\t\t\t\t\t\t\"Salut\": [{\n" +
                "\t\t\t\t\t\t\t\"confidence\": 0.97918783158635,\n" +
                "\t\t\t\t\t\t\t\"value\": \"Salut tinere\",\n" +
                "\t\t\t\t\t\t\t\"_entity\": \"Salut\"\n" +
                "\t\t\t\t\t\t}]\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}]\n" +
                "\t}]\n" +
                "}";

        FacebookMessage facebookMessage = objectMapper.readValue(json, FacebookMessage.class);

        facebookMessage.getEntries().forEach(entry -> entry.getMessageDataList().forEach(messageData -> {
            assertThat(messageData.getUserId()).isEqualTo("1514469385321837");
            assertThat(messageData.getMessage().getResponse().getMaxHighConfidence().get().getResponseText()).isEqualTo("Salut tinere");
        }));
    }
}