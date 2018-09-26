package ro.cookie.bot.service;

public interface MessageSender {
    void sendMessage(String userId, String response);
    void sendTypingOn(String userId);
}
