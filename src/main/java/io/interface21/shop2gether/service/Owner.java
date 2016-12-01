/*
 * Shop2gether Backend.
 *
 * This software module including the design and software principals used is and remains
 * the property of Heiko Scherrer (the initial author of the project)
 * and is submitted with the understanding that it is not to be reproduced nor copied in
 * whole or in part, nor licensed or otherwise provided or communicated to any third party
 * without their prior written consent. It must not be used in any way detrimental to the
 * interests of both authors. Acceptance of this module will be construed as an agreement
 * to the above.
 *
 * All rights of Heiko Scherrer remain reserved. Shop2gether Backend
 * is a registered trademark of Heiko Scherrer. Other products and
 * company names mentioned herein may be trademarks or trade names of their respective owners.
 * Specifications are subject to change without notice.
 */
package io.interface21.shop2gether.service;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * An Owner is the actual Owner of {@link Item Items} and is also a valid {@link User User} of the system.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@DiscriminatorValue("OWNER")
class Owner extends User {

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "T_OWNER_ITEM", joinColumns = {@JoinColumn(name = "C_OWNER_PK")}, inverseJoinColumns = @JoinColumn(name="C_ITEM_PK"))
    private List<Item> items = new ArrayList<>();

    /** Dear JPA ... */
    protected Owner() {
    }

    public Owner(String username, String password, String phonenumber, String email, boolean active, List<Item> items) {
        super(username, password, phonenumber, email, active);
        this.items = items;
    }

    Owner(String username, String phonenumber, String email, boolean active) {
        super(username, null, phonenumber, email, active);
    }

    public List<Item> getItems() {
        return items;
    }
}
