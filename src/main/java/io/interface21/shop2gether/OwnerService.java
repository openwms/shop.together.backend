package io.interface21.shop2gether;

/**
 * An OwnerService is a business service that deals with Owners.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface OwnerService<T extends ItemVO> {

    /**
     * Find and return an OwnerVO identified by it's persistent identifier.
     *
     * @param id The persistent identifier
     * @return The OwnerVO instance
     * @throws org.ameba.exception.NotFoundException If no OwnerVO found
     */
    OwnerVO<T> findById(Long id);

    /**
     * Save dedicated values of the passed OwnerVO instance to the instance identified by it's persistent identifier.
     *
     * @param id The persistent identifier of the instance to update
     * @param toSave Stores the actual values to be updated
     * @return The updated instance
     * @throws org.ameba.exception.NotFoundException If no OwnerVO found
     */
    OwnerVO<T> save(Long id, OwnerVO<T> toSave);

    /**
     * Save an Item for an Owner identified by id.
     *
     * @param id The persistent key of the Owner
     * @param item The Item to add to the Owner
     * @return The updated Owner instance
     */
    OwnerVO<T> save(Long id, ItemVO item);
}
