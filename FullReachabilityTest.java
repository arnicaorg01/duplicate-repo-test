import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class FullReachabilityTest {

    public static void main(String[] args) {
        String host = "arnica.example.com"; // replace with actual Arnica host
        int tcpPort = 443;                  // typical HTTPS port
        String httpUrl = "https://arnica.example.com"; // replace with Arnica service URL

        System.out.println("=== Arnica Reachability Test ===");
        testPing(host);
        testTcpPort(host, tcpPort);
        testHttp(httpUrl);
    }

    private static void testPing(String host) {
        try {
            InetAddress inet = InetAddress.getByName(host);
            if (inet.isReachable(5000)) {
                System.out.println("[PING] " + host + " is reachable");
            } else {
                System.out.println("[PING] " + host + " is NOT reachable");
            }
        } catch (IOException e) {
            System.out.println("[PING] Error reaching " + host);
            e.printStackTrace();
        }
    }

    private static void testTcpPort(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            System.out.println("[TCP] " + host + ":" + port + " is reachable");
        } catch (IOException e) {
            System.out.println("[TCP] " + host + ":" + port + " is NOT reachable");
        }
    }

    private static void testHttp(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();

            int code = connection.getResponseCode();
            if (code >= 200 && code < 300) {
                System.out.println("[HTTP] " + urlString + " is reachable (HTTP " + code + ")");
            } else {
                System.out.println("[HTTP] " + urlString + " returned HTTP code: " + code);
            }
        } catch (IOException e) {
            System.out.println("[HTTP] " + urlString + " is NOT reachable");
        }
    }
}
