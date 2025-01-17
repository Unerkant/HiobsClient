package HiobsClient.controller;

import HiobsClient.configuration.WebConfig;
import HiobsClient.service.ApiService;
import HiobsClient.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.net.http.HttpResponse;

/**
 * Den 3.11.2024
 */

@Controller
public class SperreController {

    @Autowired
    private WebConfig webConfig;
    @Autowired
    private ApiService apiService;
    @Autowired
    private AuthService authService;


    @GetMapping(value = "/sperre")
    public String getSperre(Model model) {

        /**
         * Prüfen, ob sperrzeit vorhanden ist
         * sperrInMillis: 1735678800000 → Datum in Millisekunden ist vorhanden
         *                null → keine sperrzeit vorhanden
         *  ACHTUNG: attributeName: sperreInMillis → ist bei sperre.html einen hidden INPUT
         *           da wird die sperrzeit eingefügt und dann von javascript ausgelesen...
         *           sperreCountdown.js Zeile: 17
         *
         *           attributeName: sperrJS → wenn keine sperrdatum vorhanden ist, sperrt den javascript
         */
        Long sperrInMillis   = authService.sperreMillis();
        if (sperrInMillis != null) {
            model.addAttribute("sperreInMillis", sperrInMillis);
            model.addAttribute("sperrJS", sperrInMillis);
        }

        /**
         * sperrInMillis: 1732392960000 → bei vorhandenen sperrdatum
         *                null →  zurück zum IndexController
         */
        return sperrInMillis != null ? "sperre" : "redirect:/";
    }


    /**
     * START: sperreCountdown.js/fetch
     * <br><br>
     * AUFGABE: Update die spalte 'sperrdatum' in 2 Datenbanken
     *          1. H2 HiobsClient/auth (hier)
     *          2. MySql HiobsServer/GlobalHiobs/Usern
     *
     * @return an fetch/javascript
     * result: 0 oder 1
     */
    @DeleteMapping("/sperreDelete")
    public ResponseEntity<Integer> sperreDelete() {

        /**
         * MYSQL UPDATE
         * <br><br>
         * HiobsServer/ApiSperreController/sperreApi
         * MYSQL: globalHiobs/Usern/ spalte 'sperrdatum', auf null setzen
         */
        String token = authService.authToken();
        String link = webConfig.FILE_HTTP+"sperreDeleteApi";
        HttpResponse<String> output = apiService.requestApi(link, token);

        /**
         * H2 UPDATE
         * <br><br>
         * in der H2 datenbank hiobsClient/Auth spalte 'sperrdatum', auf null setzten (null voraussetzung)
         *
         * return: 1 oder null
         * return: sperreCountdown.js/fetch zum Weiterleiten
         */
        Integer result = authService.sperreUpdate(null );

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}
