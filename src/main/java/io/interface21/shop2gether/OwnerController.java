package io.interface21.shop2gether;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
            owner.getItems().forEach(i -> {
                owner.add(linkTo(methodOn(ItemController.class).getItemFor(i.getPersistentKey())).withRel("_items"));
            });
        }
        return owner;
    }

    @GetMapping("/owners")
    List<OwnerVO> getOwners() {
        return ownerService.findAll();
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

    @DeleteMapping("/owners/{pKey}")
    String delete(@PathVariable String pKey) {
        LOGGER.debug("Deleting owner with pKey [{}]", pKey);
        return ownerService.delete(pKey);
    }
}
