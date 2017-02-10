package io.interface21.shop2gether.service;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import io.interface21.shop2gether.Coordinate;
import io.interface21.shop2gether.VerificationVO;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ameba.exception.NotFoundException;
import org.ameba.integration.jpa.ApplicationEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

import static io.interface21.shop2gether.service.Owner.*;

/**
 * An Owner is the actual Owner of {@link Item Items} and is the user of the system.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@ToString(exclude = "password")
@EqualsAndHashCode
@AllArgsConstructor
@Table(name = Owner.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = "UC_UNAME_ACTIVE", columnNames = {COLUMN_USERNAME, COLUMN_ACTIVE}),
                @UniqueConstraint(name = "UC_EMAIL_ACTIVE", columnNames = {COLUMN_EMAIL, COLUMN_ACTIVE})
        })
@Entity
class Owner extends ApplicationEntity {

    public static final String TABLE_NAME = "T_USER";
    public static final String COLUMN_USERNAME = "C_USERNAME";
    public static final String COLUMN_PASSWORD = "C_PASSWORD";
    public static final String COLUMN_PHONE = "C_PHONE";
    public static final String COLUMN_EMAIL = "C_EMAIL";
    public static final String COLUMN_ACTIVE = "C_ACTIVE";

    @NotNull
    @Column(name = COLUMN_USERNAME, nullable = false)
    private String username;
    @Column(name = COLUMN_PASSWORD)
    private String password;
    @Column(name = COLUMN_PHONE)
    private String phonenumber;
    @Column(name = COLUMN_EMAIL)
    private String email;
    @Column(name = COLUMN_ACTIVE)
    private boolean active = true;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "longitude", column = @Column(name = "C_HOME_LONG")),
            @AttributeOverride(name = "latitude", column = @Column(name = "C_HOME_LATI")),
            @AttributeOverride(name = "longitudeDelta", column = @Column(name = "C_HOME_LONG_D")),
            @AttributeOverride(name = "latitudeDelta", column = @Column(name = "C_HOME_LATI_D"))
    })
    private Coordinate home;

    /**
     * Homeposition internally used for querying.
     */
    @Column(name = "C_HOME_POS")
    private Point homePosition;
    @Column(name = "C_CODE")
    private String verificationCode;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CODE_SENT")
    private Date verificationCodeSent;

    @OrderBy("lastModifiedDt desc")
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "T_OWNER_ITEM", joinColumns = {@JoinColumn(name = "C_OWNER_PK")}, inverseJoinColumns = @JoinColumn(name = "C_ITEM_PK"))
    private Set<Item> items = new HashSet<>();

    private LinkedList<Coordinate> interestedArea = new LinkedList<>();

    /**
     * Dear JPA ...
     */
    protected Owner() {
    }

    public Owner(String username) {
        this.username = username;
    }


    public Owner(String username, String email, Coordinate homePosition) {
        this.username = username;
        this.email = email;
        this.home = homePosition;
    }

    public Set<Item> getItems() {
        return items;
    }

    public Optional<Item> getItem(Long persistentKey) {
        return items.stream().filter(i -> i.getpKey().equals(persistentKey)).findFirst();
    }

    public void updateItem(Item toSave) {
        items.stream().filter(i -> i.getpKey().equals(toSave.getpKey())).findFirst().orElseThrow(NotFoundException::new).copyFrom(toSave);
    }

    void setVerification(VerificationVO verification) {
        this.verificationCode = verification.getCode();
        this.phonenumber = verification.getPhonenumber();
        this.verificationCodeSent = new Date();
    }

    @PrePersist
    @PreUpdate
    protected void onPostConstruct() {
        if (home != null) {
            this.homePosition = new GeometryFactory().createPoint(new com.vividsolutions.jts.geom.Coordinate(home.getLongitude(), home.getLatitude()));
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public void setHomePosition(Coordinate homePosition) {
        this.home = homePosition;
    }

    public void throwIfInvalid(VerificationVO verification) {
        if (!verification.codeEquals(this.verificationCode)) {
            throw new IllegalArgumentException("Verificationcode does not match the previously sent one");
        }
        if (!verification.hasExpired(this.verificationCodeSent)) {
            throw new IllegalArgumentException("Verificationcode expired");
        }
    }
}
