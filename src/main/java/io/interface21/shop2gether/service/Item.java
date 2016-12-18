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

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * An Item is the thing that is handled or dealed with in the application. Items can be shared between groups of users.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name ="T_ITEM")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "C_TYPE")
@DiscriminatorValue("ITEM")
@EntityListeners(AuditingEntityListener.class)
abstract class Item {

    @Id
    @Column(name = "C_PK")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PkGenerator")
    @TableGenerator(table = "T_SEQUENCES", name = "PkGenerator")
    private Long pk;

    /** Optimistic locking field (Property name {@code version} might be used differently, hence lets call it {@code ol}). */
    @Version
    @Column(name = "C_OL")
    private long ol;

    /** Timestamp when the database record was inserted. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CREATED")
    @CreatedDate
    private Date createDt;

    /** Timestamp when the database record was updated the last time. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_UPDATED")
    @LastModifiedDate
    private Date lastModifiedDt;

    /** Dear JPA ... */
    protected Item() {
    }

    /**
     * Checks whether this entity is a transient one or not.
     *
     * @return {@literal true} if transient, {@literal false} if detached or managed but not transient
     */
    public boolean isNew() {
        return pk == null;
    }

    /**
     * Get the primary key value.
     *
     * @return The primary key, may be {@literal null} for transient entities.
     */
    public Long getPk() {
        return pk;
    }

    /**
     * Get the current optimistic locking version number.
     *
     * @return The number of the optimistic locking field
     */
    protected long getOl() {
        return ol;
    }

    /**
     * Get the date when the entity was persisted.
     *
     * @return creation date
     */
    public Date getCreateDt() {
        return createDt;
    }

    /**
     * Get the date when the entity was modified.
     *
     * @return last modified date
     */
    public Date getLastModifiedDt() {
        return lastModifiedDt;
    }

    @OneToMany
    @JoinTable(name = "T_ITEM_UG", joinColumns = {@JoinColumn(name = "C_ITEM_PK")}, inverseJoinColumns = @JoinColumn(name="C_UG_PK"))
    private List<UserGroup> sharedWith = new ArrayList<>();

    /** If this item is shared with others thi is set to false, if it is a private item (not shared) it is true. */
    @Column(name = "C_SHAREABLE")
    private boolean shareable = true;

    /** Shared with the list of UserGroups. */
    public Item(List<UserGroup> sharedWith) {
        this.sharedWith = sharedWith;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shared) {
        this.shareable = shareable;
    }
}
