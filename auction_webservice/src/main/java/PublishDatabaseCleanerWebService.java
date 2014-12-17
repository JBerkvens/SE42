import javax.xml.ws.Endpoint;
import webService.WebAuction;
import webService.WebDatabaseCleaner;

public class PublishDatabaseCleanerWebService {

    private static final String url = "http://localhost:8082/auctionDatabaseCleaner";

    public static void main(String[] args) {
        Endpoint.publish(url, new WebDatabaseCleaner());
    }
}
