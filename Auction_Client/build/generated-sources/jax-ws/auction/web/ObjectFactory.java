
package auction.web;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the auction.web package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CleanResponse_QNAME = new QName("http://web.auction/", "cleanResponse");
    private final static QName _Clean_QNAME = new QName("http://web.auction/", "clean");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: auction.web
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Clean }
     * 
     */
    public Clean createClean() {
        return new Clean();
    }

    /**
     * Create an instance of {@link CleanResponse }
     * 
     */
    public CleanResponse createCleanResponse() {
        return new CleanResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CleanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.auction/", name = "cleanResponse")
    public JAXBElement<CleanResponse> createCleanResponse(CleanResponse value) {
        return new JAXBElement<CleanResponse>(_CleanResponse_QNAME, CleanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Clean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web.auction/", name = "clean")
    public JAXBElement<Clean> createClean(Clean value) {
        return new JAXBElement<Clean>(_Clean_QNAME, Clean.class, null, value);
    }

}
