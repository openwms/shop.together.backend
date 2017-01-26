package io.interface21.shop2gether.service;

import javax.persistence.EntityManager;

import io.interface21.shop2gether.Coordinate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A DataPopulator used to generate some test data. Disable in production
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Configuration
class DataPopulator {

    @Bean
    CommandLineRunner clr(Repositories.OwnerRepository repo, Repositories.UserGroupRepository ugRepo, Repositories.TextNoteRepository tnRepo, EntityManager em) {
        return args -> {

            // !!!! Disable in Production !!!! //

            Owner heiko = new Owner("heiko", "heiko@home.com", new Coordinate(7.350166, 49.450632, 0.0421, 0.0922));
            heiko.setPhonenumber("4711");
            repo.save(heiko);
            UserGroup ug = ugRepo.save(new UserGroup(heiko, "Family"));

            Owner luente = new Owner("Antikatelier Luending", "luente@home.com", new Coordinate(7.350690, 49.448634, 0.0421, 0.0922));
            heiko.setPhonenumber("0815");
            repo.save(luente);
            ug.add(luente);

            Owner roland = new Owner("Metzgerei Reis", "bulle-rolland@home.com", new Coordinate(7.350548, 49.448233, 0.0421, 0.0922));
            heiko.setPhonenumber("42");
            repo.save(roland);
            ug.add(roland);

            TextNote text1 = new TextNote("Shoppinglist 1", "1 x 10 Eggs\n2 x Milk\n1 x Peanutbutter\n3 x Oranges\n1 big Pineapple\nSome cheese\n1pd. Meatballs", "#E9E74A", false);
            text1 = tnRepo.save(text1);
            TextNote text2 = new TextNote("My secret list", "Toothbrush", "#EE5E9F", false);
            text2.setShareable(false);
            text2 = tnRepo.save(text2);
            TextNote text3 = new TextNote("Postoffice Today", "Letter to post office\n10 x Stamps\nAsk for next delivery", "#FFDD2A", false);
            text3 = tnRepo.save(text3);
            TextNote text4 = new TextNote("From the hardware store", "1000 wood screws and anchors\nBacklight for mower\nIs there any good metal saw\n10in.x15in. piece of wood", "#FFDD2A", false);
            text4 = tnRepo.save(text4);

            heiko.getItems().add(text1);
            heiko.getItems().add(text2);
            heiko.getItems().add(text3);
            heiko.getItems().add(text4);

            repo.save(heiko);
            text1.getSharedWith().add(ug);
            text2.getSharedWith().add(ug);
            text3.getSharedWith().add(ug);
            text4.getSharedWith().add(ug);
            text1 = tnRepo.save(text1);
        };
    }
}
