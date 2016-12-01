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
package io.interface21.shop2gether;

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * A OwnerController exposes RESTful Owner resources.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RestController
class OwnerController<T extends ItemVO> {

    private final OwnerService<T> ownerService;

    OwnerController(OwnerService<T> ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners/{id}")
    OwnerVO getOwnerFor(@PathVariable Long id) {
        OwnerVO<T> owner = ownerService.getById(id);
        if (!owner.getItems().isEmpty()) {

            // enrich
            owner.getItems().forEach(i -> {
                owner.add(new Link("http://localhost:8080/items/" + i.getPersistentKey(), "items"));
//                owner.add(linkTo(methodOn(ItemController.class).getItemFor(i.getPersistentKey())).withRel("_items"));
            });
        }
        return owner;
    }
}
