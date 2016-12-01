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

import io.interface21.shop2gether.ItemService;
import io.interface21.shop2gether.ItemVO;
import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;

/**
 * A ItemServiceImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class ItemServiceImpl implements ItemService {

    private final BeanMapper mapper;
    private final Repositories.ItemRepository repository;

    ItemServiceImpl(BeanMapper mapper, Repositories.ItemRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public ItemVO getById(Long id) {
        Item item = repository.findOne(id);
        NotFoundException.throwIfNull(item, String.format("No Item with id %s exists!", id));
        return mapper.map(item, ItemVO.class);
    }
}
