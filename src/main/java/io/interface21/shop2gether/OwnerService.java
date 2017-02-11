package io.interface21.shop2gether;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * An OwnerService is a business service that deals with Owners.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface OwnerService<T extends ItemVO> {

    /**
     * Find and return an OwnerVO identified by it's persistent identifier.
     *
     * @param pKey The persistent identifier
     * @return The OwnerVO instance
     * @throws org.ameba.exception.NotFoundException If OwnerVO not found
     */
    OwnerVO<T> findByPKey(String pKey);

    /**
     * Update an existing OwnerVO instance, identified by it's persistent identifier,
     * with values from .
     *
     * @param pKey   The persistent identifier of the instance to update
     * @param toSave Stores the actual values to be updated
     * @return The updated instance
     * @throws org.ameba.exception.NotFoundException If no OwnerVO found
     */
    OwnerVO<T> save(String pKey, OwnerVO<T> toSave);

    /**
     * Save an Item of an Owner.
     *
     * @param pKey The persistent key of the Owner
     * @param item The Item to the Owner to added or updated
     * @return The updated Owner instance
     */
    OwnerVO<T> save(@NotNull String pKey, @NotNull ItemVO item);

    List<OwnerVO> findAll();
}
