package auction.domain;

import auction.domain.Item;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-05T11:07:10")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SetAttribute<User, Item> offeredItems;
    public static volatile SingularAttribute<User, String> email;

}