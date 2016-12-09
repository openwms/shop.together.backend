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
 * A ItemController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RestController
class ItemController {

    private final ItemService itemService;

    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @GetMapping("/items/{id}")
    ItemVO getUserGroupsForItems(@PathVariable Long id) {
        ItemVO item = itemService.getById(id);
        if (!item.getSharedWith().isEmpty()) {

            // enrich
            item.getSharedWith().forEach(i -> {
                item.add(new Link("https://shop2gether.herokuapp.com/usergroups/" + i.getPk(), "usergroups"));

                // todo: until this bug is not fixed we have to stick on the line above instead... https://github.com/spring-projects/spring-hateoas/issues/169
//                owner.add(linkTo(methodOn(ItemController.class).getItemFor(i.getPersistentKey())).withRel("_items"));
            });
        }
        return item;
    }

}
