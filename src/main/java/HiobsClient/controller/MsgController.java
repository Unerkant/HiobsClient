package HiobsClient.controller;

import HiobsClient.configuration.WebConfig;
import HiobsClient.model.Friends;
import HiobsClient.service.ApiService;
import HiobsClient.service.AuthService;
import HiobsClient.utilities.GeoLocation;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.net.http.HttpResponse;

/**
 * Den 13.02.2025
 */

@Controller
public class MsgController {

    @Autowired
    private WebConfig webConfig;
    @Autowired
    private GeoLocation geoLocation;
    @Autowired
    private AuthService authService;
    @Autowired
    private ApiService apiService;
    private String friendsFragment = "friends"; // als Standard genommen

    @GetMapping(value = {"/msg", "/msg/{param}", "/msg/{param}/{anker}"})
    public ModelAndView msg(HttpServletRequest request,
                            @PathVariable(required = false, name = "param") String msgToken,
                            @PathVariable(required = false, name = "anker") String msgAnker,
                            Model model) {

        String meinToken = authService.authToken();

        /* anmeldung prüfen */
        if (meinToken == null) {
            return new ModelAndView("login/maillogin");
        }


        /**
         * Daten für der LeftColumn
         * ====================================================================
         * 1. Freunde Fragment Laden
         * 2. Hover Effect für unter Manü
         * 3. Hiobs Post Kanal, Datum & letzte Nachricht holen
         * 4. Gespeichertes Kanal, Datum & letzte Nachricht holen
         * 5. Alle Freunde aus dem Datenbankladen & anzeigen. Quelle: Hiobs Server/secondary/model/Friends
         */

        // 1. fragment friends.html(<th:block data-th-fragment="friendsfragment">) Laden: index.html Zeile: 30
        model.addAttribute("leftFragmentName", friendsFragment );


        // 2. Request Path an fragments/components/footermenubar senden (visited)
        model.addAttribute("msgRequestUri", "/msg");


        // 3. alle Freunde anzeigen: fragment(friends.html): index.html/34,
        String friendsResponse = datenLaden("allFriends", meinToken);

        System.out.println("MsgController meinToken: " + meinToken);
        System.out.println("Response: " + friendsResponse);

                // response in einem Friends Object umwandeln, für Thymeleaf data-th-each="" ausgabe
        Friends[] friends = friendsResponse == null ? null : apiService.arrayToEachArray(friendsResponse);
                // Friends ArrayObject an html senden
        model.addAttribute("freundeDaten", friends);
                // URL von Profil Bild
        String profilbildURL = webConfig.SERVER_PROFILBILD;
        model.addAttribute("profilBildUrl", profilbildURL);


        // 4 hover:effect an angeklickten Freund setzen (css class append, friends.html Zeile: 63... & weitere )
        model.addAttribute("msgToken", msgToken);



        /**
         *    Daten für den rightColumn
         *    =================================================================
         *
         *  msgToken → das ist einen message Token, zugesendet mit dem click aus der Freund (friends.html)
         *              oder einer Kanal-message-token(@123....) oder aus hamburger menü (string)
         *
         * 1. IF:       01234567890:  ohne '@' nur Nummer-zeichen → msg von Freund, mit textarea
         * 2. ELES IF:  @01234567890: mit @ → msg-channel(nachrichten kanal), ohne textarea!
         * 3. ELSE IF:  click aus dem hamburger menü: <a data-th-href="@{/msg/createchannel}" >...</a>
         * 4. ELSE:     nicht machen
         *
         * ACHTUNG: wenn msgToken leer ist, dann geht es ins else rein(nicht machen)
         */
        model.addAttribute("slideRightsShows", "sliderRightShow");

        if(msgToken != null && msgToken.matches("-?\\d+(\\.\\d+)?")) {

            // Freund message(MSG) anzeigen
            model.addAttribute("rightFragmentName", "msg");

            // Angeklickten Freund Daten holen, für den MSG-Header(msg.html)
            String freundDaten = datenLaden( "oneFriend", msgToken);
            Friends[] meinfreund = apiService.arrayToEachArray(freundDaten);
            model.addAttribute("meinFreund", meinfreund);

            // Message aus der Datenbank holen, HiobsServer/ApiMsgController/msgLaden (http:8080//Localhost)
            String msgDaten = datenLaden( "msgLaden", msgToken);
            // message Daten senden an msg.html + msgsearch.html(fragment in msg.html)
            model.addAttribute("msgdaten", msgDaten != null ? msgDaten : "leer" );

            // *************************** zum Löschen ***************************************
            // UNTER DATE WAREN NUR ZUM TESTEN:
            // msgsearch.html/data-th-onKeyUp="searchMessage([[${testJson}]],'AUSGABEITEM', this.value )"
            String json = "[{\"email\":\"example@example.com fafavav a as va v\"," +
                    "\"other\":\"total ACHTUNG: zu Testen bitte admin oder user eingeben, weil role wird " +
                    "durchgesucht(Zeile 82), MsgController, Ein Freund Daten: [LHiobsClient.model." +
                    "Friends;@1fc7e3d2\",\"role\":\"admin\"}" +
                    ",{\"email\":\"example@ex.com asvasv asv as vas\",\"other\": " +
                    "\"MsgController, Ein Freund Daten: [LHiobsClient.model.Friends;@1fc7e3d2, " +
                    "MsgController, Ein Freund Daten: [LHiobsClient.model.Friends;@1fc7e3d2,MsgController, " +
                    "Ein Freund Daten: [LHiobsClient.model.Friends;@1fc7e3d2\", \"role\": \"user\"}" +
                    ",{\"email\": \"example@example.com\", \"other\": \"einzeln\", \"role\": \"admin\"}" +
                    ",{\"email\": \"example@example.com\", \"other\": \"keineahnung\", \"role\": \"admin\"}]";
            model.addAttribute("testJson", json);
            // ***********************************************************************************************
            System.out.println("MsgController, Ein Freund Daten: " + msgDaten );

        } else if (msgToken != null && msgToken.matches("-?@\\d+(\\.\\d+)?")){

            // Öffentlicher Kanal anzeigen(ohne Recht zu Kommentieren)
            model.addAttribute("rightFragmentName", "channel");

            System.out.println("MsgController, Canal Ausgabe: " + msgToken);

        } else if (msgToken != null) {

            System.out.println("MsgController, Message Ausgabe: " + msgToken);
            // anzeigen aus hamburger menü
            switch (msgToken) {
                case "invite":          model.addAttribute("rightFragmentName", msgToken);
                                        break;
                case "createchannel":   model.addAttribute("rightFragmentName", msgToken);
                                        break;
                case "newgroup":        model.addAttribute("rightFragmentName", msgToken);
                                        break;
                case "createsecretchat":model.addAttribute("rightFragmentName", msgToken);
                                        break;
                default: model.addAttribute("linkFehler", true);  break;
            }

        } else {
            // nicht machen
            System.out.println("MsgController, ELSE, nicht tun: " + msgToken );
        }

        // Ausgabe von index.html
        return new ModelAndView("index");
    }


    /**
     * Alle Request Abfrage zu HiobsServer (HiobsServer/Api....)
     *
     * @param token
     * @return          null oder Array
     */
    public String datenLaden(String path, String token) {

        String msgLadenPath = webConfig.SERVER_HTTP+path;
        HttpResponse<String> response = apiService.requestApi(msgLadenPath, token);

        return response.body().isBlank() ? null : response.body();
    }

}
