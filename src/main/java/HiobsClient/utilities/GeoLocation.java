package HiobsClient.utilities;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Den 18.12.2024
 */

@Component
public class GeoLocation {

    /**
     * Geo-Position von User Ermitteln von BigData frei gestellten API
     * Link: https://www.bigdatacloud.com/free-api#apilist
     * * mit Stern werden die API Benutzt
     *
     *    * A: FREE Client Side Reverse Geocoding to City API
     *          GET: https://us1.api-bdc.net/data/reverse-geocode-client ODER https://api-bdc.net/data/reverse-geocode-client
     *          Parameter:
     *          1   {
     *          2       "continent":"Europe",
     *          3       "continentCode":"EU",
     *          4       "countryCode":"DE",
     *          5       "countryName":"Germany",
     *          6       "city":"Lauterbach",
     *          7       "locality":"Lauterbach",
     *          8       "principalSubdivisionCode":"DE-HE",
     *          9      "principalSubdivision":"Hessen",
     *          10      "postcode":"36341",
     *          11      "plusCode":"9F2FJ9JX+2X",
     *          12      "latitude":50.630001068115234,
     *          13      "longitude":9.399999618530273,
     *          14      "lookupSource":"ip geolocation",
     *          15      "localityLanguageRequested":"en"
     *          16  }
     *
     *    * C: FREE Public IP Address API
     *          GET: https://us1.api-bdc.net/data/client-ip ODER https://api-bdc.net/data/client-ip
     *          Parameter:
     *          1   {
     *          2       "ipString": "18.205.245.190",
     *          3       "ipType": "IPv4"
     *          4   }
     *
     *      B: FREE Am I Roaming API            (NICHT BENUTZT)
     *          GET: https://us1.api-bdc.net/data/am-i-roaming (ermitteln von latitude & longitude)
     *          Query: https://us1.api-bdc.net/data/am-i-roaming?latitude=36.93&longitude=-78.92
     *          ODER ohne us1, https://api-bdc.net/data/am-i-roaming
     *          Parameter:
     *          1   {
     *          2       "isRoaming": false
     *          3   }
     *
     *      D: FREE Client Info API     (NICHT BENUTZT)
     *          GET: https://us1.api-bdc.net/data/client-info ODER https://api-bdc.net/data/client-info
     *          Parameter:
     *          1   {
     *          2       "ipString":"18.205.245.190",
     *          3       "ipNumeric":315487678,
     *          4       "ipType":"IPv4",
     *          5       "isBehindProxy":false,
     *          6       "device":"Mobile",
     *          7       "os":"Other",
     *          8       "userAgent":"Other",
     *          9       "family":"Other",
     *          10      "isSpider":false,
     *          11      "isMobile":true,
     *          12      "userAgentDisplay":"Other Mobile Other",
     *          13      "userAgentRaw":""
     *          14  }
     *
     *  von Controller Daten Abfrage
     *  Erforderlich: die Parameter('city' oder locality ...) soll mitgesendet sein um gezielte Daten zu holen)
     *  <code>
     *      @Autowired
     *      private GeoLocation geoLocation;
     *
     *      String geo= geoLocation.clientCity("city");
     *      String ip = geoLocation.clientIp();
     *      //output: Msg Controller: Lauterbach/95.223.108.40
     *  </code>
     */


    /**
     * FREE Client Side Reverse Geocoding to City API
     * Erlaubte param nur von Position A
     *
     * <code>
     *      erforderliche oder häufig benutzte parameter:
     *      ausschnitt von json-array:
     *                  {"continent":"Europe","countryName":"Germany", "principalSubdivision":"Hessen",
     *                                                          "city":"Lauterbach", "postcode":"36341"}
     *      Controller: String geo= geoLocation.clientCity("city");
     * </code>
     * @param param
     * @return
     */
    public String clientCity(String param) {
        //
        JSONObject geo = clientGeo();
        return (geo != null ? (geo.opt(param) == null ? "Unbekannt" : geo.get(param).toString()) : "Unbekannt");
    }
    //gehört zu clientCity(){...}
    public JSONObject clientGeo() {

        String urlString = "";
        String current;
        try {
            URL url = new URL("https://api-bdc.net/data/reverse-geocode-client");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            while ((current = in.readLine()) != null) {
                urlString += current;
            }

            JSONObject object = new JSONObject(urlString);
            return object;

        } catch (IOException e) {
            return null;
        }
    }


    /**
     *  FREE Public IP Address API(von api-bdc...)
     *  erlaubte key nur von position C
     *
     * Eine Alternative zu Ip-Abfrage von ipify.org
     * Link: https://www.ipify.org/
     * GET: https://www.ipify.org/      // ...URL("https://api.ipify.org").openStream()...
     * ACHTUNG: änderung bei alternative, nur links + return ändern und der JSONObject auskommentieren/Löschen
     * <code>
     *     try (java.util.Scanner s = new java.util.Scanner(
     *                 new java.net.URL("https://api.ipify.org").openStream(), "UTF-8")
     *                 .useDelimiter("\\A")) {
     *             return s.next();
     *             }...catch bleibt
     * </code>
     */
    public String clientIp() {
        try (java.util.Scanner s = new java.util.Scanner(
                new java.net.URL("https://api-bdc.net/data/client-ip").openStream(), "UTF-8")
                .useDelimiter("\\A")) {

            JSONObject obj = new JSONObject(s.next());
            return obj.opt("ipString").toString();

        } catch (java.io.IOException e) {
            //e.printStackTrace();
            return "Unbekannt";
        }
    }


    /* ************************** ZURZEIT NICHT BENUTZT **************************** */

    /**
     * ACHTUNG: ZURZEIT NICHT BENUTZT
     * es sind kein benötige information, außer die IP-Adresse, über der User vorhanden,
     * siehe der Abschnitt (oben) D an...
     * aber die IP-Adresse kann direct über die funktion clientIp(){...} beziehen...
     *
     * @param param
     * @return
     */
    public String clientInfo(String param) {

        JSONObject info = clientDevice();
        return (info != null ? (info.opt(param) == null ? "Unbekannt" : info.get(param).toString()) : "Unbekannt");
    }
    // gehört zu clientInfo(){...}
    private JSONObject clientDevice() {

        String urlString = "";
        String current;
        try  {
            URL url = new URL("https://api-bdc.net/data/client-info");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            while ((current = in.readLine()) != null) {
                urlString += current;
            }

            JSONObject object = new JSONObject(urlString);
            //output: object {"ipString":"95.223.108.40","ipType":"IPv4"}
            return object;
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        }
    }

}
