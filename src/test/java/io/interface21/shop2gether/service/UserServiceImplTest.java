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
package io.interface21.shop2gether.service;

import java.util.LinkedList;

import io.interface21.shop2gether.BackendApplication;
import io.interface21.shop2gether.Coordinate;
import io.interface21.shop2gether.UserService;
import org.ameba.app.BaseConfiguration;
import org.ameba.integration.jpa.BaseEntityTest;
import org.ameba.integration.jpa.IntegrationTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * A UserServiceImplTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = IntegrationTestConfig.class)
@ComponentScan(basePackageClasses = {BackendApplication.class, BaseConfiguration.class})
public class UserServiceImplTest {

    @Autowired private UserService srv;
    @Autowired private TestEntityManager em;

    public @Test void testWithinArea() {
        User foreigner = new User("foreigner", "", "", true, new Coordinate(0,0,0,0));
        User friend1 = new User("friend1", "", "", true, new Coordinate(1.1,0.1,0,0));
        em.persist(foreigner);
        //em.persist(friend1);

        LinkedList<Coordinate> area = new LinkedList<>();
        area.add(new Coordinate(1.0, 1.0, 0, 0));
        area.add(new Coordinate(2.0, 1.0, 0, 0));
        area.add(new Coordinate(1.0, 2.0, 0, 0));
        area.add(new Coordinate(2.0, 2.0, 0, 0));
        srv.findUsersWithin(area);
    }
}
