package io.interface21.shop2gether.service;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import io.interface21.shop2gether.Coordinate;
import io.interface21.shop2gether.VerificationVO;
import lombok.*;
import org.ameba.integration.jpa.ApplicationEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Date;

import static io.interface21.shop2gether.service.User.*;

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

    public User(String username) {
        this.username = username;
    }

    public User(String username, String email, Coordinate homePosition) {
        this.username = username;
        this.email = email;
        this.home = homePosition;
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
}
