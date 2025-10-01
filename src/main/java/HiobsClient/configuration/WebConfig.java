package HiobsClient.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

/**
 * Den 24.10.2024
 */

@Configuration
public class WebConfig {

    @PostConstruct
    public void init() {
        // mach was
    }


    public final String LOGIN_CSS               = "http://localhost:8090/static/style/login.css";
    public final String INDEX_CSS               = "http://localhost:8090/static/style/chat.css";
    public final String FILE_URL                = "???";
    public final String SERVER_PROFILBILD       = "http://localhost:8080/profilbild/";
    //public final String SERVER_PROFILBILD     = "https://hiobspost.de/profilbild/";
    public final String SERVER_HTTP             = "http://localhost:8080/";
    //public final String SERVER_HTTP           = "http://194.164.63.85:8080/";
    /* Bild Adresse BEI BOTE:  http://localhost:8080/profilbild/03052022103644.png */
}
