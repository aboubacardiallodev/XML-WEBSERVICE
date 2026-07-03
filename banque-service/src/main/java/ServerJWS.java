import jakarta.xml.ws.Endpoint;
import ws.BanqueAccountService;

public class ServerJWS {

    public static void main(String[] args) {
        String url = "http://localhost:8081";

        Endpoint.publish(url, new BanqueAccountService());

        System.out.println("Service déployé !");
        System.out.println("web service disponible sur : " + url);
    }
}
