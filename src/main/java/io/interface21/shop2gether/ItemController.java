package io.interface21.shop2gether;

import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A ItemController exposes RESTful Item resources.
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

                // todo: until this bug is not fixed we're stuck on the line above instead... https://github.com/spring-projects/spring-hateoas/issues/169
//                owner.add(linkTo(methodOn(ItemController.class).getItemFor(i.getPersistentKey())).withRel("_items"));
            });
        }
        return item;
    }


    @DeleteMapping("/items/{id}")
    void delete(@PathVariable Long id) {
        itemService.delete(id);
    }

    @PatchMapping("/items/{id}")
    void patch(@PathVariable Long id, @RequestBody ItemVO item) {
        itemService.updatePartially(id, item);
    }
}
