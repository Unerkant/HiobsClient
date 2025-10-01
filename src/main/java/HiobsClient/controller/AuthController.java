package HiobsClient.controller;

import HiobsClient.configuration.WebConfig;
import HiobsClient.model.Auth;
import HiobsClient.model.Exception;
import HiobsClient.service.ApiService;
import HiobsClient.service.AuthService;
import HiobsClient.utilities.MyUtilities;
import HiobsClient.utilities.GeoLocation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.http.HttpResponse;

/**
 * Den 28.03.2025
 */

@Controller
public class AuthController {

    @Autowired
    private WebConfig webConfig;
    @Autowired
    private GeoLocation geoLocation;
    @Autowired
    private MyUtilities myUtilities;
    @Autowired
    private AuthService authService;
    @Autowired
    private ApiService apiService;

    @PostMapping(path = "/auth")
    public ResponseEntity<String> auth(@RequestBody String token ) {

        Auth output = authService.authDelete();
        String result = output == null ? "abgemeldet" : "nichtabgemeldet";

        // wenn nicht abgemeldet wird
        if (output != null) { errorMelden(); }

        System.out.println("Auth request: " + output + " / " + result);

        return output == null ? ResponseEntity.status(HttpStatus.OK).body(result) : null ;
    }

    /**
     * ERROR Melden, speichern in http://localhost:8080/, HiobsServer, Database: globalHiobs, Tabelle: Exception
     * Logout error, wenn User Daten werde aus Datenbank nicht gelöscht!
     * Database: hiobsClient, Tabelle Auth (soll nur einen eintrag sein)
     */
    private void errorMelden() {

        Exception except = new Exception();
        except.setDatum(myUtilities.deDatum());
        except.setErrip(geoLocation.clientInfo("ipString"));
        except.setErrcode(400);
        except.setErrquelle("http://localhost:8090/h2-console, database: hiobsClient, Tabelle: Auth");
        except.setErrtext("Abmeldung nicht M&#246;glich, Tabelle Auth wird nicht geleert/gel&#246;scht,");

        //
        String exceptUrl = webConfig.SERVER_HTTP+"exception";
        JSONObject objectJson = new JSONObject(except);
        HttpResponse<String> output = apiService.requestApi(exceptUrl, objectJson.toString());
        //System.out.println("Response: " + output.body());
    }
}
