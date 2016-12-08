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
    CommandLineRunner clr(Repositories.OwnerRepository repo, Repositories.UserGroupRepository ugRepo, Repositories.TextNoteRepository tnRepo) {
        return args -> {
            Owner heiko = repo.save(new Owner("heiko", "4711", "heiko@home.com", true));
            UserGroup ug = ugRepo.save(new UserGroup(heiko, "Family"));

            TextNote text1 = new TextNote("Shoppinglist 1", "1 x Eggs\n2 x Milk\n1x Peanutbutter", "#E9E74A", false);
            text1 = tnRepo.save(text1);
            TextNote text2 = new TextNote("Shoppinglist 2", "Toothbrush", "#ffffff", false);
            text2 = tnRepo.save(text2);

            heiko.getItems().add(text1);
            heiko.getItems().add(text2);

            repo.save(heiko);
            text1.getSharedWith().add(ug);
            text2.getSharedWith().add(ug);
            text1 = tnRepo.save(text1);
//            text2 = tnRepo.save(text2);

        };
    }

}
