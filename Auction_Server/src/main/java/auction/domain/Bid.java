package auction.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bid implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bid_seq")
    @SequenceGenerator (
            name="bid_seq",
            sequenceName = "bidd_seq",
            allocationSize = 1,
            initialValue = 1
    )
    private long bidID;

    @Embedded
    private FontysTime time;
    
    @ManyToOne
    private User buyer;
    
    @Embedded
    private Money amount;
    
    public Bid () {
        
    }

    public Bid(User buyer, Money amount) {
        time = new FontysTime();
        this.buyer = buyer;
        this.amount = amount;
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }
}
