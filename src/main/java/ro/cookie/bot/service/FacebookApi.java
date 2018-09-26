package ro.cookie.bot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class FacebookApi {
    private final String facebookUrl;
    private final String facebookApiVersion;
    private final String accessToken;

    public FacebookApi(
            @Value("${facebook.url}")
                    String facebookUrl,
            @Value("${facebook.apiVersion}")
                    String facebookApiVersion,
            @Value("${facebook.accessToken}")
            String accessToken) {
        this.facebookUrl = facebookUrl;
        this.facebookApiVersion = facebookApiVersion;
        this.accessToken = accessToken;
    }

    public String getApiForSendMessage(){
        return UriComponentsBuilder.fromHttpUrl(facebookUrl)
                .path(facebookApiVersion)
                .path("/me/messages")
                .queryParam("access_token", accessToken)
                .build().toUriString();
    }
}
