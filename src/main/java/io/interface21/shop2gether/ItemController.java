package io.interface21.shop2gether;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * A ItemController exposes RESTful Item resources.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RestController
class ItemController {

    static final String RESOURCE_PLURAL = "/items";
    private final ItemService itemService;

    ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(RESOURCE_PLURAL + "/{pKey}")
    ItemVO getItemFor(@PathVariable String pKey) {
        ItemVO item = itemService.getByPKey(pKey);
        if (!item.getSharedWith().isEmpty()) {

            // enrich
            item.getSharedWith().forEach(i -> {
                item.add(linkTo(methodOn(UserGroupController.class).getUserGroupFor
                        (item.getPersistentKey())).withRel("usergroups"));
            });
        }
        ;
        item.add(linkTo(methodOn(ItemController.class).getItemFor(pKey)).withSelfRel());
        return item;
    }


    @DeleteMapping(RESOURCE_PLURAL + "/{id}")
    void deleteSingleItem(@PathVariable String id) {
        itemService.delete(id);
    }

    @PatchMapping(RESOURCE_PLURAL + "/{id}")
    void patchItem(@PathVariable String id, @RequestBody ItemVO item) {
        itemService.updatePartially(id, item);
    }
}
