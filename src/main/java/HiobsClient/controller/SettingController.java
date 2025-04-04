package HiobsClient.controller;

import HiobsClient.service.AuthService;
import HiobsClient.utilities.MyUtilities;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


/**
 * Den 29.01.2024
 */

@Controller
public class SettingController {

    @Autowired
    private AuthService authService;
    @Autowired
    private MyUtilities myUtilities;

    @Value("${project.artifactId}")
    private String artifact;
    @Value("${project.version}")
    private String version;

    @GetMapping(value = {"/setting", "/setting/{cat}", "/setting/{cat}/{subcat}"})
    public ModelAndView setting(HttpServletRequest request,
                                @PathVariable(required = false, name = "cat") String category,
                                @PathVariable(required = false, name = "subcat") String subCategory,
                                Model model) {

        System.out.println("Get Mapping: " + category);

        /* anmeldung prüfen */
        if (authService.authToken() == null) {
            return new ModelAndView("login/maillogin");
        }

        /**
         * ACHTUNG: Aktuell wird es die anzeige von setting-category auf dem rechte seite nur mit
         * neuer aktualisierung die seite gemacht, die category wird als parameter hier zugesendet
         * data-th-onClick="window.location.href = '/setting/editprofile'"
         * FAZIT: Es sollte probieren in der zukunft die category auf der rechte seite mit der
         * javascript versuchen, wie die fragment die 2 reihe
         * <a class="finger" data-th-onClick="otherRequest('/blockedUsers', [[${myToken}]])">Blockerte Nutzer</a>
         * oterFragment.js function: otherRequest(){... innerHTML = response ...}
         *
         *  hier sollen die eingene Daten von globalHiobs datenbank holen für weitere verarbeitung,
         *  die untere alle ausgaben, sollen nochmal neu sortiert sein
         *  z.b.s die allgemeine angaben wie, hover
         *  z.b.s die version sollte ganz am schluss stehen
         */

        System.out.println("SettingController myDaten: " + authService.authDaten());

        /* ausgabe in 2 reihe: security.html, für javascript:otherFragment(als zweiten param) später Löschen */
        model.addAttribute("myToken", "01234567890");


        /* Senden Fragment Name an index.html/unter menü  */
        model.addAttribute("leftFragmentName", "/setting");
        if(category != null) {
            model.addAttribute("rightFragmentName", "settingCategory");
            model.addAttribute("category", category);
        }
        /* class für slider effect, Senden an index.html, wenn rightFragmentName vorhanden ist, hier oben */
        model.addAttribute("slideRightsShows", "sliderRightShow");

        /* Project Version, Senden an setting.html */
        model.addAttribute("profilActiv", category);
        model.addAttribute("version", artifact + "@ " + version);

        /* für hover effect in Fragment:: footermenubar.html */
        model.addAttribute("settingRequestUri", "/setting");

        /* Language */
        model.addAttribute("land", myUtilities.getLanguage());

        return new ModelAndView("index");
    }

}
