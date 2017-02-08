package io.interface21.shop2gether.service;

import io.interface21.shop2gether.Coordinate;
import org.ameba.exception.NotFoundException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

/**
 * An Owner is the actual Owner of {@link Item Items} and is also a valid {@link User User} of the system.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@DiscriminatorValue("OWNER")
class Owner extends User {

    @OrderBy("lastModifiedDt desc")
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "T_OWNER_ITEM", joinColumns = {@JoinColumn(name = "C_OWNER_PK")}, inverseJoinColumns = @JoinColumn(name="C_ITEM_PK"))
    private Set<Item> items = new HashSet<>();

    private LinkedList<Coordinate> interestedArea = new LinkedList<>();

    /** Dear JPA ... */
    protected Owner() {
    }

    public Owner(String username) {
        super(username);
    }

    public Owner(String username, String email, Coordinate homePosition) {
        super(username, email, homePosition);
    }

    public Set<Item> getItems() {
        return items;
    }

    public Optional<Item> getItem(Long persistentKey) {
        return items.stream().filter(i -> i.getPk().equals(persistentKey)).findFirst();
    }

    public void updateItem(Item toSave) {
        items.stream().filter(i -> i.getPk().equals(toSave.getPk())).findFirst().orElseThrow(NotFoundException::new).copyFrom(toSave);
    }
}
