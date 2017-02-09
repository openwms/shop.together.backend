package io.interface21.shop2gether.service;

import com.vividsolutions.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * A Repositories.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
interface Repositories {

    interface ItemRepository extends JpaRepository<Item, Long> {

        <T extends Item> Optional<T> findByPKey(String pKey);
    }

    interface TextNoteRepository extends JpaRepository<TextNote, Long> {

    }

    interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    }

    interface OwnerRepository extends JpaRepository<Owner, Long> {

        Optional<Owner> findByUsername(String username);

        Optional<Owner> findByPhonenumber(String phonenumber);

        @Query(value = "select u from Owner u where within(u.homePosition, :area) = true")
        List<Owner> findUsersWithin(@Param("area") Polygon area);

        Optional<Owner> findByPKey(String pKey);
    }
}
