package io.interface21.shop2gether;

/**
 * An ItemService.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface ItemService {

    /**
     * Find and return an Item identified by unique id.
     *
     * @param id The persistent key
     * @return The Item, never {@literal null}
     * @throws org.ameba.exception.NotFoundException My throw in case of no Item found
     */
    ItemVO getById(Long id);

    /**
     * Delete item with the give unique id.
     *
     * @param id The persistent key
     */
    void delete(Long id);

    void updatePartially(Long id, ItemVO item);
}
