/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package io.interface21.shop2gether;

import static io.interface21.shop2gether.UserGroup.COLUMN_NAME;
import static io.interface21.shop2gether.UserGroup.COLUMN_OWNER;

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

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.ameba.integration.jpa.ApplicationEntity;

/**
 * A UserGroup.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Getter
@ToString
@EqualsAndHashCode
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
}
