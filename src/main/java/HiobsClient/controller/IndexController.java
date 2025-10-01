package HiobsClient.controller;

import HiobsClient.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Den 30.09.2024
 */

@Controller
public class IndexController {

    @Autowired
    private AuthService authService;

    @GetMapping(value = {"", "/"})
    public ModelAndView index(HttpServletRequest request, Model model){

        /**
         * IF: wenn Sperre verhängt ist, eintragung in Millisekunden in spalte 'sperrdatum'
         *    z.b.s  (31.12.2024 22:00:00 in Millisekunden: 1735678800000)
         * else IF: wenn token existiert, spalte 'token' → 123456789, wechseln zu Message Chat
         * ELSE: wenn datenbank: Tabelle 'AUTH' leer ist, login starten
         */
        if (authService.sperreMillis() != null) {
            return new ModelAndView("redirect:/sperre");
        } else if (authService.authToken() != null){
            return new ModelAndView("redirect:/msg");
        } else {
            return new ModelAndView("redirect:/login/qrlogin");
        }
    }

}
