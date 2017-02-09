package io.interface21.shop2gether.service;

import java.util.List;
import java.util.Optional;

import com.vividsolutions.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * A Repositories.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
interface Repositories {

    @RepositoryRestResource(collectionResourceRel = "items", path = "items", exported = true)
    interface ItemRepository extends JpaRepository<Item, Long> {

    }
/*
    @RepositoryRestResource(collectionResourceRel = "owners", path = "owners")
    interface OwnerRepository extends JpaRepository<Owner, Long> {

    }
*/
    @RepositoryRestResource(collectionResourceRel = "textitems", path = "textitems", exported = false)
    interface TextNoteRepository extends JpaRepository<TextNote, Long> {

    }

    @RepositoryRestResource(collectionResourceRel = "usergroups", path = "usergroups", exported = false)
    interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    }

    interface OwnerRepository extends JpaRepository<Owner, Long> {

        Optional<Owner> findByUsername(String username);

        Optional<Owner> findByPhonenumber(String phonenumber);

        @Query(value = "select u from Owner u where within(u.homePosition, :area) = true")
        List<Owner> findUsersWithin(@Param("area") Polygon area);
    }
}
