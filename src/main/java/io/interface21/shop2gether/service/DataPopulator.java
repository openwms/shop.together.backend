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

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A DataPopulator.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
class DataPopulator {

    @Bean
    CommandLineRunner clr(Repositories.OwnerRepository repo, Repositories.UserGroupRepository ugRepo) {
        return args -> {
            Owner heiko = repo.save(new Owner("heiko", "4711", "heiko@home.com", true));
            UserGroup ug = ugRepo.save(new UserGroup(heiko, "Family"));

            TextNote text1 = new TextNote("Shoppinglist 1", "1 x Eggs; 2 x Milk", "#cecece", false);
            TextNote text2 = new TextNote("Shoppinglist 2", "Toothbrush", "#ffffff", false);

            heiko.getItems().add(text1);
            heiko.getItems().add(text2);

            repo.save(heiko);
            text1.getSharedWith().add(ug);
            text2.getSharedWith().add(ug);

        };
    }

}
