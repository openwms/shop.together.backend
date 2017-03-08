package io.interface21.shop2gether.service;

import lombok.Getter;
import org.ameba.integration.jpa.ApplicationEntity;

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

import static io.interface21.shop2gether.service.UserGroup.COLUMN_NAME;
import static io.interface21.shop2gether.service.UserGroup.COLUMN_OWNER;
import static io.interface21.shop2gether.service.UserGroup.TABLE_NAME;

/**
 * An UserGroup is used to group a set of Users in order to assign Items to them.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@Entity
@Table(name = TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(name = "UC_OWNER_NAME", columnNames = {COLUMN_OWNER, COLUMN_NAME})
})
class UserGroup extends ApplicationEntity {

    public static final String TABLE_NAME = "T_USER_GROUP";
    public static final String COLUMN_OWNER = "C_OWNER";
    public static final String COLUMN_NAME = "C_NAME";

    /**
     * Dear JPA...
     */
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
    @JoinTable(name = "T_UG_USER", joinColumns = {@JoinColumn(name = "C_UG_PK")}, inverseJoinColumns = @JoinColumn(name = "C_U_PK"))
    private List<Owner> users = new ArrayList<>();

    /**
     * Add a User to this UserGroup.
     *
     * @param user The User to add
     * @return see {@link List#add(Object)}
     */
    public boolean add(Owner user) {
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserGroup userGroup = (UserGroup) o;
        return Objects.equals(owner, userGroup.owner) &&
                Objects.equals(name, userGroup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, name);
    }
}
