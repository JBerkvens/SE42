import javax.xml.ws.Endpoint;
import webService.WebAuction;
import webService.WebRegistration;

public class PublishRegistrationWebService {

    private static final String url = "http://localhost:8081/auctionRegistration";

    public static void main(String[] args) {
        Endpoint.publish(url, new WebRegistration());
    }
}
