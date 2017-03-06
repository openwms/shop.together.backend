package io.interface21.shop2gether;

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

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
                item.add(linkTo(methodOn(UserGroupController.class).getItemFor(i.get()))
                        .withRel("_items"));
                item.add(new Link("https://shop2gether.herokuapp.com/usergroups/" + i.getPk(), "usergroups"));
        }
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
