package HiobsClient.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Den 24.10.2024
 */

@Service
public class ApiService {

    /**
     *      // GESENDET von HiobsClient/Controller
     *      <br><br>
     *         ***  NÖTIGE DATEN zum request senden, nur Link + Json oder String
     *         String link = "http://localhost:8080/sperreApi";
     *         String json = "{\"key\":\"value\"}";
     *         String jsonSend = "{\"email\": \""+mail+"\"}";
     *         String mail = example@example.com
     *         HttpResponse<String> response = apiService.requestApi(link, json);
     *
     *         *** ERHALTENE DATEN von request-response
     *         System.out.println("Request Status: " + response.statusCode());
     *         System.out.println("Request response: " + response.body());
     *         response: 200 oder 204(NO_CONTENT) oder 404(NO_FOUND) oder 405(NO_PATH) oder 500
     *
     *      // GESENDET an HiobsServer/ ApiController
     *      <br><br>
     *         *** gesendet an HiobsServer/ApiSperreController
     *         @PostMapping(value = "/sperreApi")
     *         public ResponseEntity<String> apiSperre(@RequestBody String sperre) {
     *              // sperre -> zugesendeten json {"key":"value"}
     *              //String responseJson = "{\"status\":\"success\",\"message\":\"Admin data received\"}";
     *              return ResponseEntity.status(HttpStatus.OK).body(responseJson);
     *          }
     *         return stausCode(200 oder 204...404...510) + json oder Text
     *
     * @param apiUrl
     * @param apiParam
     * @return
     */

    public HttpResponse<String> requestApi(String apiUrl, String apiParam) {

        HttpResponse<String> response = null;
        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("Accept","application/json")
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(apiParam, StandardCharsets.UTF_8))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Throwable ex) {
            // Fehler ausgabe
            //System.out.println("Exception: " + ex);
            //ex.printStackTrace();
        }

        return response;
    }
}
