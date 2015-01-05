
import auction.domain.Item;
import javax.xml.ws.Endpoint;
import webService.WebAuction;
import webService.WebDatabaseCleaner;
import webService.WebRegistration;

public class PublishAllWebServices {

    private static final String urlAuction = "http://localhost:8080/auction";
    private static final String urlRegistration = "http://localhost:8081/auctionRegistration";
    private static final String urlDatabaseCleaner = "http://localhost:8082/auctionDatabaseCleaner";
    private static final String urlItems = "http://localhost:8083/auctionItems";

    public static void main(String[] args) {
        Endpoint.publish(urlAuction, new WebAuction());
        Endpoint.publish(urlRegistration, new WebRegistration());
        Endpoint.publish(urlDatabaseCleaner, new WebDatabaseCleaner());
    }
}
