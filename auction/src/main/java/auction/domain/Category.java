package auction.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

public class Category {

    private String description;

    private Category() {
        description = "undefined";
    }

    public Category(String description) {
        this.description = description;
    }

    public String getDiscription() {
        return description;
    }
}
