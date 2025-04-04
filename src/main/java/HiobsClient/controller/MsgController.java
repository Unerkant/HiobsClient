package HiobsClient.controller;

import HiobsClient.service.AuthService;
import HiobsClient.service.FriendsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Den 13.02.2025
 */

@Controller
public class MsgController {

    @Autowired
    private AuthService authService;
    @Autowired
    private FriendsService friendsService;
    private String friendsFragment = "friends"; // als Standard genommen

    @GetMapping(value = {"/msg", "/msg/{param}", "/msg/{param}/{anker}"})
    public ModelAndView msg(HttpServletRequest request,
                            @PathVariable(required = false, name = "param") String msgToken,
                            @PathVariable(required = false, name = "anker") String msgAnker,
                            Model model) {
        /* anmeldung prüfen */
        if (authService.authToken() == null) {
            return new ModelAndView("login/maillogin");
        }

        // Daten für der LeftColumn
        // fragment Name an index.html senden, Zeile: 30
        model.addAttribute("leftFragmentName", friendsFragment );
        // Request Path an fragments/components/footermenubar senden (visited)
        model.addAttribute("msgRequestUri", "/msg");
        // alle Freunde aus den datenbank: hiobsClient/friends laden und an friends.html übergeben
        model.addAttribute("freundeDaten", friendsService.freundeLaden());
        // angeklickte Freund hover:effect (css class aktivieren, friends.html)
        model.addAttribute("clickActiv", msgToken);

        System.out.println("Freunde Daten: " + friendsService.freundeLaden());

        // später zum loschen
        String myToken = "09876543210";
        String result = "@"+myToken;
        model.addAttribute("kanalToken", result);
        model.addAttribute("msgToken", myToken);
        model.addAttribute("msgAnker", "1234567");

        /**
         *    Daten für den rightColumn
         *
         *  msgToken → das ist einen message Token, zugesendet mit dem click aus der Freund (friends.html)
         *              oder einer Kanal-message-token(@123....) oder aus hamburger menü (string)
         *
         * 1. @01234567890: mit @ → msg-channel(nachrichten kanal), ohne textarea!
         * 2. 01234567890:  ohne '@' nur Nummer-zeichen → msg von Freund, mit textarea
         * 3. click aus dem hamburger menü: <a data-th-href="@{/msg/createchannel}" >...</a>
         */
        model.addAttribute("slideRightsShows", "sliderRightShow");

        if(msgToken != null && msgToken.contains("@")) {

            // Öffentlicher Kanal anzeigen(ohne Recht zu Kommentieren)
            model.addAttribute("rightFragmentName", "channel");

        } else if (msgToken != null && msgToken.matches("-?\\d+(\\.\\d+)?")){

            // Freund message(MSG) anzeigen
            model.addAttribute("rightFragmentName", "msg");

        } else if (msgToken != null) {

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
            //System.out.println("ELSE: " + msgToken +"/"+ msgAnker);
        }

        // Ausgabe von index.html
        return new ModelAndView("index");
    }

}
