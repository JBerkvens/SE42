import javax.xml.ws.Endpoint;
import webService.WebAuction;

public class PublishMainWebService {

    private static final String url = "http://localhost:8080/auction";

    public static void main(String[] args) {
        Endpoint.publish(url, new WebAuction());
    }
}
