package HiobsClient.configuration;

import HiobsClient.utilities.MyUtilities;
import HiobsClient.utilities.UrlResolver;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Den 24.10.2024
 */

@Configuration
public class WebConfig {


    @Autowired
    private UrlResolver urlResolver;
    public WebConfig(UrlResolver urlResolver1) {
        this.urlResolver = urlResolver1;
    }

    @PostConstruct
    public void init() {
        //System.out.println("Config: " + urlResolver.getHost());
        if (urlResolver.getHost().equals("localhost")){

            //System.out.println("HOST: " + urlResolver.getHost());

        } else {

        }
    }


    public final String LOGIN_CSS               = "/static/css/login.css";
    public final String CHAT_CSS                = "/static/css/chat.css";
    public final String FILE_URL                = "???";
    public final String SERVER_HTTP               = "http://localhost:8080/";
    //public final String SERVER_HTTP               = "http://194.164.63.85:8080/";
    /* Bild Adresse BEI BOTE:  http://localhost:8080/profilbild/03052022103644.png */
}
