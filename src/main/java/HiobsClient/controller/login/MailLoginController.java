package HiobsClient.controller.login;

import HiobsClient.configuration.WebConfig;
import HiobsClient.model.Auth;
import HiobsClient.model.Exception;
import HiobsClient.service.ApiService;
import HiobsClient.service.AuthService;
import HiobsClient.utilities.MyUtilities;
import HiobsClient.utilities.UrlResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.net.http.HttpResponse;


/**
 * Den 27.11.2024
 */

@Controller
public class MailLoginController {

    @Autowired
    private WebConfig webConfig;
    @Autowired
    private AuthService authService;
    @Autowired
    private ApiService apiService;
    @Autowired
    private MyUtilities myUtilities;
    @Autowired
    private UrlResolver urlResolver;

    private Boolean mailerrors  = false;
    private int codeVersuch     = 0;


    @GetMapping(value = "/login/maillogin")
    public ModelAndView getMail(HttpServletRequest request, Model model) {

        // auf anmeldung prüfen
        if (authService.authToken() == null) {
            return new ModelAndView("login/maillogin");
        }


        model.addAttribute("errorsmail", mailerrors);
        //System.out.println("GET, Mail Login: " + authService.authToken());

        return new ModelAndView("redirect:/");
    }


    /**
     * BENUTZT: von idcode.html
     * <br><br>
     *
     *      * GESENDET: mit requestApi -> an HiobsServer/ApiLoginController ->  @PostMapping(value = "/loginApi")
     *      * wird gesendet nur reine E-Mail als String, KEIN JSON (example@example.com)
     *
     * response daten von HiobsServer/ApiLoginController →  @PostMapping(value = "/loginApi")
     * response.statusCode → 400 + 'nosend', wenn code nicht versendet wird(nicht erforderlich)
     * response.statusCode → 200, wenn E-Mail ist versendet
     * response.body() → 'gefunden' oder 'nichtgefunden', E-Mail vorhanden ist oder nein
     *
     * FAZIT: bei erfolgreichen versenden eine e-mail mit aktivierungCode gehts es weiter zu idcode.html,
     *        bei einem Fehler von Mail-Sender, wird den Fehler ins Datenbank eingetragen, den Fehler
     *        wird mit requestApi an HiobsServer/GlobaleException gesendet
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/login/maillogin")
    public String getPost(HttpServletRequest request, Model model) {

        // Sicherheitscode versenden
        String loginLink = webConfig.SERVER_HTTP+"loginMail";
        String email = request.getParameter("email");
        HttpResponse<String> response = apiService.requestApi(loginLink, email);

        if(response.statusCode() == 200) {

            // nach erfolgreichen versenden den AktivierungCode, weiter zu idCode.html
            model.addAttribute("mailadresse", email);
            model.addAttribute("mailfinden", response.body()); // 'gefunden' oder 'nichtgefunden'

            return "login/idcode";

        } else {

            /**
             * weiter an fehlerMelden(){...} senden
             * <br><br>
             * dieser Fehler ist von HiobsServer/ApiLoginController -> mail-server
             */
            String errQuelle = "HiobsClient/MailController";
            String errText = "Den Fehler kommt von response -> HiobsClient/MailController/ApiService.requestApi";
            fehlerMelden(response.statusCode(), errQuelle, errText );

            // Fehler von Mailer anzeigen in maillogin.html
            mailerrors = true;
            return "redirect:/login/maillogin";
        }

    }


    /**
     * Anmelde Daten werden an HiobsServer/ApiLoginController/@PostMapping(value = "/loginSave") gesendet
     * gesendete Daten:
     *      code: eingegebene sicherheitscode vor die E-Mail
     *      mail: eingegeben E-Mail-adresse
     *      fund: 'gefunden' oder 'nichtgefunden', ob die E-Mail in Datenbank vorhanden ist oder nein,
     *             wird in @PostMapping(value = "/login/mail") geprüft, beim Versenden die E-Mail mit
     *             dem sicherheit-code
     *
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/login/save")
    public String getSave(@CookieValue(value = "JSESSIONID", required = false) String cookie,
            HttpServletRequest request,
            Model model) {



        // Date sammeln und an HiobsServer senden
        String eins = request.getParameter("codeEins");
        String zwei = request.getParameter("codeZwei");
        String drei = request.getParameter("codeDrei");
        String vier = request.getParameter("codeVier");
        String total = eins + zwei + drei + vier;

        String userMail = request.getParameter("usermail");
        String mailFund = request.getParameter("mailfund");

        String codeLink = webConfig.SERVER_HTTP+"loginSave";
        String userSend = "{\"email\": \""+userMail+"\", \"other\": \""+total+"\", \"role\": \""+mailFund+"\"}";

        // Daten an HiobsServer senden zum ApiLoginController/@PostMapping(value = "/loginSave")
        HttpResponse<String> output = apiService.requestApi(codeLink, userSend);


        /**
         * ****** output.body() *******
         *
         *    output: {"datum":"21-12-2024 16:21:01","other":"","passwort":"","role":"",
         *             "sperrdatum":0,"sprache":"de","pseudonym":"ri","token":"21122024162101","bild":"",
         *             "uservorname":"","telefon":"","id":0,"email":"example@example.de","username":""}
         */

        JSONObject object = new JSONObject(output.body());
        String mail = object.getString("email");
        String wert = object.getString("other");
        String fund = object.getString("role");

        if (wert.equals("falschecode")){

            // Falsche Sicherheit code, nur 4 versuche
            model.addAttribute("mailadresse", mail);
            model.addAttribute("falschecode", wert);
            model.addAttribute("mailfinden", fund);
            model.addAttribute("codeblock", codeVersuch);

            if (codeVersuch > 3) {
                codeVersuch = 0;
            }

            codeVersuch = codeVersuch + 1;
            return "login/idcode";

        } else if (wert.equals("nosave") ){

            // speicherung in Datenbank fehlgeschlagen, HiobsServer/ApiLoginController/loginSave Zeile: ~150
            model.addAttribute("codeblock", 10);
            model.addAttribute("nosave", wert);

            return "login/idcode";

        } else {

            //Registrierung/Einloggen erfolgreich, in H2/Auth Daten speichern
            String other = object.getString("other");
            String role = object.getString("role");
            String token = object.getString("token");
            Long sperre = object.optLong("sperrdatum") == 0 ? null : object.getLong("sperrdatum");

            // Daten für Datenbank vorbereiten
            Auth auth = new Auth();
            auth.setId(1);
            auth.setDatum(myUtilities.deDatum());
            auth.setMail(mail);
            auth.setOther(other);
            auth.setRole(role);
            auth.setToken(token);
            auth.setSperrdatum(sperre);

            Auth loginAuth = authService.loginSave(auth);
            //System.out.println("Success: " + loginAuth);

            model.addAttribute("userObject", object);
            model.addAttribute("userCookie", cookie);

            return "login/success";
        }

    }



    /**
     * Fehler in globalen Datenbank speichern
     * <br><br>
     * senden per request an HiobsServer/GlobaleException
     *
     * return: res -> 'gespeichert' in String
     *
     * @param statusCode
     * @param fehlerQuelle
     * @param fehlerText
     */
    private void fehlerMelden(int statusCode, String fehlerQuelle, String fehlerText ) {

        String fehlerLink = webConfig.SERVER_HTTP+"exception";
        Exception except = new Exception();

        except.setDatum(myUtilities.deDatum());
        except.setErrip(urlResolver.getNetzwerkIp());
        except.setErrcode(statusCode);
        except.setErrquelle(fehlerQuelle);
        except.setErrtext(fehlerText);

        JSONObject sendExcept = new JSONObject(except);
        HttpResponse<String> res = apiService.requestApi(fehlerLink, sendExcept.toString());

    }

}
