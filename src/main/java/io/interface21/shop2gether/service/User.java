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

import static io.interface21.shop2gether.service.User.COLUMN_ACTIVE;
import static io.interface21.shop2gether.service.User.COLUMN_EMAIL;
import static io.interface21.shop2gether.service.User.COLUMN_USERNAME;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.ameba.integration.jpa.ApplicationEntity;

/**
 * An User is some authenticated human user of the system.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorColumn(name = "C_TYPE")
@DiscriminatorValue("USER")
@Table(name = "T_USER",
        uniqueConstraints = {
                @UniqueConstraint(name = "UC_UNAME_ACTIVE", columnNames = {COLUMN_USERNAME, COLUMN_ACTIVE}),
                @UniqueConstraint(name = "UC_EMAIL_ACTIVE", columnNames = {COLUMN_EMAIL, COLUMN_ACTIVE})
        })
class User extends ApplicationEntity {

    public static final String COLUMN_USERNAME = "C_USERNAME";
    public static final String COLUMN_PASSWORD = "C_PASSWORD";
    public static final String COLUMN_PHONE = "C_PHONE";
    public static final String COLUMN_EMAIL = "C_EMAIL";
    public static final String COLUMN_ACTIVE = "C_ACTIVE";

    @Column(name = COLUMN_USERNAME)
    private String username;

    @Column(name = COLUMN_PASSWORD)
    private String password;

    @Column(name = COLUMN_PHONE)
    private String phonenumber;
    @Column(name = COLUMN_EMAIL)
    private String email;
    @Column(name = COLUMN_ACTIVE)
    private boolean active;
}
