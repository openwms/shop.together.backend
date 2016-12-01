/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
