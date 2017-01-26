package io.interface21.shop2gether.service;

import static io.interface21.shop2gether.service.User.COLUMN_ACTIVE;
import static io.interface21.shop2gether.service.User.COLUMN_EMAIL;
import static io.interface21.shop2gether.service.User.COLUMN_USERNAME;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import io.interface21.shop2gether.Coordinate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ameba.integration.jpa.ApplicationEntity;

/**
 * An User is an authenticated human user of the system.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@ToString(exclude = "password")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorColumn(name = "C_TYPE")
@DiscriminatorValue("USER")
@Table(name = User.TABLE_NAME,
        uniqueConstraints = {
                @UniqueConstraint(name = "UC_UNAME_ACTIVE", columnNames = {COLUMN_USERNAME, COLUMN_ACTIVE}),
                @UniqueConstraint(name = "UC_EMAIL_ACTIVE", columnNames = {COLUMN_EMAIL, COLUMN_ACTIVE})
        })
class User extends ApplicationEntity {

    public static final String TABLE_NAME = "T_USER";
    public static final String COLUMN_USERNAME = "C_USERNAME";
    public static final String COLUMN_PASSWORD = "C_PASSWORD";
    public static final String COLUMN_PHONE = "C_PHONE";
    public static final String COLUMN_EMAIL = "C_EMAIL";
    public static final String COLUMN_ACTIVE = "C_ACTIVE";

    @Column(name = COLUMN_USERNAME, nullable = false)
    private String username;
    @Column(name = COLUMN_PASSWORD)
    private String password;
    @Column(name = COLUMN_PHONE)
    private String phonenumber;
    @Column(name = COLUMN_EMAIL, nullable = false)
    @NotNull
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

    /** Homeposition internally used for querying. */
    @Column(name = "C_HOME_POS")
    private Point homePosition;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, Coordinate homePosition) {
        this.username = username;
        this.email = email;
        this.home = homePosition;
    }

    @PrePersist
    @PreUpdate
    protected void onPostConstruct() {
        this.homePosition = new GeometryFactory().createPoint(new com.vividsolutions.jts.geom.Coordinate(home.getLongitude(), home.getLatitude()));
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
}
