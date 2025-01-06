package HiobsClient.controller.login;

import HiobsClient.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Den 14.10.2024
 */

@Controller
public class QrLoginController {

    @Autowired
    private AuthService authService;


    @GetMapping(value = "/login/qrlogin")
    public String getQrcode(Model model){

        //System.out.println("Get Mapping QR-Code: ");

        /**
         * wenn Tabelle 'AUTH' leer ist, login starten
         */
        return authService.authDaten() == null ? "login/qrlogin" : "redirect:/";
    }

}
