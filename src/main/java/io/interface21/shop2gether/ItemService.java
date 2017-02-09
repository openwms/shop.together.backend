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
     * @param pKey The persistent key
     * @return The Item, never {@literal null}
     * @throws org.ameba.exception.NotFoundException My throw in case of no Item found
     */
    ItemVO getByPKey(String pKey);

    /**
     * Delete item with the give unique id.
     *
     * @param pKey The persistent key
     */
    void delete(String pKey);

    void updatePartially(String pKey, ItemVO item);
}
