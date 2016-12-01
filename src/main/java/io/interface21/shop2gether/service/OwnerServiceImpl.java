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
package io.interface21.shop2gether.service;

import io.interface21.shop2gether.ItemVO;
import io.interface21.shop2gether.OwnerService;
import io.interface21.shop2gether.OwnerVO;
import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;

/**
 * A OwnerServiceImpl is a Spring managed transactional service that deals with OwnerVO instances.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class OwnerServiceImpl<T extends ItemVO> implements OwnerService<T> {

    private final BeanMapper mapper;
    private final Repositories.OwnerRepository repository;

    public OwnerServiceImpl(BeanMapper mapper, Repositories.OwnerRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OwnerVO<T> getById(Long id) {
        Owner owner = repository.findOne(id);
        NotFoundException.throwIfNull(owner, String.format("Owner with id %s does not exist", id));
        return mapper.map(owner, OwnerVO.class);
    }
}
