package io.interface21.shop2gether;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A OwnerController exposes RESTful Owner resources.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RestController
class OwnerController<T extends ItemVO> {

    private final OwnerService<T> ownerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerController.class);

    OwnerController(OwnerService<T> ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners/{pKey}")
    OwnerVO getOwnerFor(@PathVariable String pKey) {
        OwnerVO<T> owner = ownerService.findByPKey(pKey);
        if (!owner.getItems().isEmpty()) {

            // enrich
            owner.getItems().forEach(i -> {
                owner.add(new Link("https://shop2gether.herokuapp.com/items/" + i.getPersistentKey(), "items"));

                // todo: until this bug is not fixed we're stuck on the line above instead... https://github.com/spring-projects/spring-hateoas/issues/169
//                owner.add(linkTo(methodOn(ItemController.class).getItemFor(i.getPersistentKey())).withRel("_items"));
            });
        }
        return owner;
    }

    @PostMapping("/owners/{pKey}")
    void save(@PathVariable String pKey, @RequestBody OwnerVO owner) {
        LOGGER.debug("Updating owner with record [{}]", owner);
        ownerService.save(pKey, owner);
    }

    @PostMapping("/owners/{pKey}/items")
    void saveItem(@PathVariable String pKey, @RequestBody ItemVO item) {
        LOGGER.debug("Updating owner, store item [{}]", item);
        ownerService.save(pKey, item);
    }
}
