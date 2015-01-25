
package auction.web;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "DatabaseCleaner", targetNamespace = "http://web.auction/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface DatabaseCleaner {


    /**
     * 
     */
    @WebMethod
    @RequestWrapper(localName = "clean", targetNamespace = "http://web.auction/", className = "auction.web.Clean")
    @ResponseWrapper(localName = "cleanResponse", targetNamespace = "http://web.auction/", className = "auction.web.CleanResponse")
    @Action(input = "http://web.auction/DatabaseCleaner/cleanRequest", output = "http://web.auction/DatabaseCleaner/cleanResponse")
    public void clean();

}