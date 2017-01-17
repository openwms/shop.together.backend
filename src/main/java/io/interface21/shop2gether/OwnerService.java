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

/**
 * An OwnerService is a component private business service that deals with Owners.
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

    OwnerVO<T> save(Long id, ItemVO item);
}
