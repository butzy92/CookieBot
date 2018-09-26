package ro.cookie.bot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.cookie.bot.service.MessageProcessor;

@RestController
public class FacebookWebhook {
    private static final Logger LOGGER = LoggerFactory.getLogger(FacebookWebhook.class);

    private final MessageProcessor messageProcessor;

    public FacebookWebhook(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @GetMapping("/webhook")
    public String webhook(@RequestParam("hub.mode") String mode,
                                 @RequestParam("hub.verify_token") String verifyToken,
                                 @RequestParam("hub.challenge") String challenge){
        LOGGER.info("Received FacebookMessage");
        LOGGER.info("Received verifyToken {}", verifyToken);
        LOGGER.info("Received mode {}", mode);
        LOGGER.info("Received challenge {}", challenge);


        return challenge;
    }

    @PostMapping("/webhook")
    public void receiveMessage(@RequestBody String body){
        LOGGER.info("Received FacebookMessage");
        LOGGER.info("Received body {}", body);

        messageProcessor.processMessage(body);
    }
}
