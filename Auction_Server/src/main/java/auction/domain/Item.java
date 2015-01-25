package auction.domain;

import java.util.Objects;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import nl.fontys.util.Money;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@NamedQueries({
    @NamedQuery(name = "Item.getAll", query = "select i from Item as i"),
    @NamedQuery(name = "Item.count", query = "select count(i) from Item as i"),
    @NamedQuery(name = "Item.findByDescription", query = "select i from Item as i where i.description = :description")
})
public class Item implements Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "it_seq")
    @SequenceGenerator (
            name="it_seq",
            sequenceName = "item_seq",
            allocationSize = 1,
            initialValue = 1
    )
    private Long id;
    
    @ManyToOne
    private User seller;
    
    @Embedded
    @AttributeOverride(name="description", column = @Column(name="categoryDescription"))
    private Category category;
    
    private String description ;
    
    @OneToOne
    private Bid highest;
    
    public Item() {
        
    }

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    @Override
    public int compareTo(Object arg0) {
        Item andere = (Item)arg0;
        if (andere.id < this.id) {
            return -1;
        } else if (andere.id > this.id) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        Item it = (Item)o;
        return Objects.equals(it.id, this.id) && it.description.equals(this.description) && it.seller.getEmail().equals(this.seller.getEmail());
    }

    @Override
    public int hashCode() {
        //TODO
        if (id != null) {
        return this.id.hashCode();
        }
        else {
            return -1;
        }
    }
}
