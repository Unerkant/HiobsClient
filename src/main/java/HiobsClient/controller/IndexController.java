package HiobsClient.controller;

import HiobsClient.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Den 30.09.2024
 */

@Controller
public class IndexController {

    @Autowired
    private AuthService authService;

    @GetMapping(value = {"/", "/index"})
    public String getIndex(){

        /**
         * IF: wenn sperre verhängt ist, eintragung in Millisekunden im spalte 'sperrdatum'
         *      (31.12.2024 22:00:00 in Millisekunden: 1735678800000)
         * else IF: wenn token existiert, spalte 'token' -> 123456789
         * ELSE: wenn Tabelle 'AUTH' leer ist, login starten
         */
        if (authService.sperreMillis() != null) {
            return "redirect:/sperre";
        } else if (authService.authToken() != null){
            return "index";
        } else {
            return "redirect:/login/qrlogin";
        }
    }
}
