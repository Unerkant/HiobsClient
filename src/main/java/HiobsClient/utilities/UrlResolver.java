package HiobsClient.utilities;

import org.springframework.stereotype.Component;
import java.net.*;

/**
 * Den 18.12.2024
 */

@Component
public class UrlResolver {


    /**
     * HTTP Localhost Name
     * <br><br>
     *
     * return: ~localhost
     *
     * @return
     */
    public String getHost() {

        try {
            return InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Localhost Ip-Adresse (127.0.0.1)
     * <br><br>
     *
     * return:  127.0.0.1
     *
     * @return  als String
     */
    public String getLocalhostIp() {

        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Deine TCP/IP Gerät-Ip-Adresse (Laptop oder ipad)
     * <br><br>
     *
     * return: 192.168.0.246
     *
     * @return
     */
    public String getNetzwerkIp() {

        String ip = "127.0.0.1";
        try(final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException ex) {
            System.out.println(ex);
        }

        return ip;
    }


}
