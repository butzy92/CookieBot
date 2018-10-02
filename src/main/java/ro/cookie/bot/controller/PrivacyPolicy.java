package ro.cookie.bot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrivacyPolicy {

    @GetMapping("/privacyPolicy")
    public String getPolicy(){
        return "privacyPolicy.html";
    }
}
