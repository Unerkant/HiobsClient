package HiobsClient.service;

import HiobsClient.model.Auth;
import HiobsClient.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Den 1.11.2024
 */

@Service
public class AuthService {

    /**
     * ACHTUNG: IN DATENBANK 'hiobsClient' SOLL NUR EINE TABELLE 'AUTH' GEBEN ZUM EINLOGGEN
     *  [Auth: {id=1, datum='15.09.2024 19:47:55', mail='sam@sam.de', other='',
     *          role='', sperre='gesperrt', sperrdatum='1730659266485', token='123456789'}]
     */

    @Autowired
    private AuthRepository authRepository;


    /**
     *  ACHTUNG: noch keine verwendung
     *
     *  size zählen, ausführung in Controller
     *   //int ids =  ((ArrayList) authService.findeAlle()).size();
     *
     * @return
     */
    public Iterable<Auth> findeAlle() {

        return authRepository.findAll();
    }


    /**
     * Anmelden: login-daten ins H2 speichern
     * <br><br>
     *
     * @param auth
     * @return
     */
    public Auth loginSave(Auth auth) { return authRepository.save(auth); }


    /**
     * Benutzt: LoginController
     * <br><br>
     * ACHTUNG: return null nur bei Leere Tabelle 'Auth'
     *
     * @return
     */
    public Auth authDaten() {

        Iterable<Auth> result = authRepository.findAll();
        if (!result.iterator().hasNext()) {
            return null;
        }
        return result.iterator().next();
    }


    /**
     * Benutzt: IndexController
     * <br><br>
     * Token holen <br>
     * !result.iterator().hasNext(),  true/false
     * wenn Tabelle AUTH leer ist, return null
     * return token: 1234567890(token) oder null
     *
     * @return
     */
    public String authToken() {

        Iterable<Auth> result = authRepository.findAll();

        // wenn Tabelle 'AUTH' leer ist, return null
        if (!result.iterator().hasNext()) {
            return null;
        }

        return result.iterator().next().getToken();
    }


    /**
     * Benutzt: SperreController
     * <br><br>
     * Sperrzeit holen <br>
     * Sperrzeit: ausgabe in Millisecond -> 1730659361065
     * <br><br>
     * output: 1730659361065 oder null
     *
     * @return
     */
    public Long sperreMillis() {

        Iterable<Auth> result = authRepository.findAll();

        // wenn Tabelle 'AUTH' leer ist, return null
        if (!result.iterator().hasNext()) {
            return null;
        }

        return result.iterator().next().getSperrdatum();
    }


    /**
     * Benutzt: Setting/ausloggen
     * <br><br>
     *
     * Tabelle 'AUTH' leeren(Ausloggen)
     * <br>
     * return: null/array (array -> wenn nicht gelöscht wird)
     * <br><br>
     *   //Abfrage von Controller: Auth geloscht = authService.authDelete();
     *                              System.out.println("return: " + geloscht);
     *                              return: null
     * @return
     */
    public Auth authDelete() {

        authRepository.deleteAll();

        // prüfen, ob gelöscht wird
        Iterable<Auth> result = authRepository.findAll();
        if (!result.iterator().hasNext()) {
            return null;
        }
        return result.iterator().next();
    }

    /**
     * Sperre Aufheben
     *
     * @param sperrdatum
     * @return
     */
    public Integer sperreUpdate(Long sperrdatum) {

       return authRepository.updateSperre( sperrdatum );

    }


    /**
     * benutzt von PhoneController/@GetMapping(value = "/phone")
     * alle Telefonate Ausgeben
     */
/*    public List<Phone> telefonatAusgeben(String token){
        List<Phone> telefonat = new ArrayList<>();
        phoneRepository.findByToken(token).forEach(telefonat::add);
        return telefonat;
    }*/

}
