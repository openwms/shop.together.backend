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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * An Item is the thing that is handled or dealed with in the application. Items can be shared between groups of users.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@ToString
@Entity
@Table(name = Item.TABLE_NAME)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "C_TYPE")
@DiscriminatorValue("ITEM")
@EntityListeners(AuditingEntityListener.class)
abstract class Item<T extends Item>  {

    public static final String TABLE_NAME = "T_ITEM";

    @Id
    @Column(name = "C_PK")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PkGenerator")
    @TableGenerator(table = "T_SEQUENCES", name = "PkGenerator")
    private Long pk;

    /**
     * Technical key field, independent from the underlying database, may assigned from application layer, remains the same over
     * database migrations. An unique constraint or limitation to not-null is explicitly not defined here, because it is a matter of
     * subclasses to defines those, if required.
     */
    @Column(name = "C_PID", nullable = false)
    private String pKey;

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

    @PrePersist
    protected void onPersist() {
        if (this.pKey == null) {
            this.pKey = UUID.randomUUID().toString();
        }
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
    @JoinTable(name = "T_ITEM_UG", joinColumns = {@JoinColumn(name = "C_ITEM_PK")}, inverseJoinColumns = @JoinColumn(name = "C_UG_PK"))
    private List<UserGroup> sharedWith = new ArrayList<>();

    /** If this item is shared with others thi is set to false, if it is a private item (not shared) it is true. */
    @Column(name = "C_SHAREABLE")
    private boolean shareable = true;

    /** Shared with the list of UserGroups. */
    public Item(List<UserGroup> sharedWith) {
        this.sharedWith = sharedWith;
    }

    public List<UserGroup> getSharedWith() {
        return sharedWith;
    }

    public String getpKey() {
        return pKey;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shared) {
        this.shareable = shareable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (pKey != null) {
            return pKey.equals(item.pKey);
        } else {
            if (item.pKey != null) return false;
        }
        if (pk != null ? !pk.equals(item.pk) : item.pk != null) return false;
        if (ol != item.ol) return false;
        if (shareable != item.shareable) return false;
        if (createDt != null ? !createDt.equals(item.createDt) : item.createDt != null) return false;
        if (lastModifiedDt != null ? !lastModifiedDt.equals(item.lastModifiedDt) : item.lastModifiedDt != null) return false;
        return sharedWith != null ? sharedWith.equals(item.sharedWith) : item.sharedWith == null;
    }

    @Override
    public int hashCode() {
        int result = pk != null ? pk.hashCode() : 0;
        result = 31 * result + (pKey != null ? pKey.hashCode() : 0);
        result = 31 * result + (int) (ol ^ (ol >>> 32));
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (lastModifiedDt != null ? lastModifiedDt.hashCode() : 0);
        result = 31 * result + (sharedWith != null ? sharedWith.hashCode() : 0);
        result = 31 * result + (shareable ? 1 : 0);
        return result;
    }

    public void copyFrom(T toSave) {
        this.shareable = toSave.isShareable();
        this.sharedWith = toSave.getSharedWith();
    }
}
