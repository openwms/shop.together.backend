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

import static io.interface21.shop2gether.service.UserGroup.COLUMN_NAME;
import static io.interface21.shop2gether.service.UserGroup.COLUMN_OWNER;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Getter;
import org.ameba.integration.jpa.ApplicationEntity;

/**
 * A UserGroup.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@Entity
@Table(name = "T_USER_GROUP", uniqueConstraints = {
        @UniqueConstraint(name = "UC_OWNER_NAME", columnNames = {COLUMN_OWNER, COLUMN_NAME})
})
class UserGroup extends ApplicationEntity {

    public static final String COLUMN_OWNER = "C_OWNER";
    public static final String COLUMN_NAME = "C_NAME";

    /** Dear JPA...*/
    protected UserGroup() {

    }

    UserGroup(Owner owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    @OneToOne
    @JoinColumn(name = COLUMN_OWNER)
    private Owner owner;
    @Column(name = COLUMN_NAME)
    private String name;
    @OneToMany
    @JoinTable(name = "T_UG_USER", joinColumns = {@JoinColumn(name = "C_UG_PK")}, inverseJoinColumns = @JoinColumn(name="C_U_PK"))
    private List<User> users = new ArrayList<>();

    /**
     * Add a User to this UserGroup.
     *
     * @param user The User to add
     * @return see {@link List#add(Object)}
     */
    public boolean add(User user) {
        return this.users.add(user);
    }

    @Override
    public String toString() {
        return "UserGroup{" +
                "owner=" + owner +
                ", name='" + name + '\'' +
                ", users=" + users +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroup userGroup = (UserGroup) o;
        return Objects.equals(owner, userGroup.owner) &&
                Objects.equals(name, userGroup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, name);
    }
}
