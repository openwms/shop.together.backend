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

import org.springframework.data.jpa.repository.JpaRepository;
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

    @RepositoryRestResource(collectionResourceRel = "owners", path = "owners")
    interface OwnerRepository extends JpaRepository<Owner, Long> {

    }

    @RepositoryRestResource(collectionResourceRel = "textitems", path = "textitems", exported = false)
    interface TextNoteRepository extends JpaRepository<TextNote, Long> {

    }

    @RepositoryRestResource(collectionResourceRel = "usergroups", path = "usergroups", exported = false)
    interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    }

}
