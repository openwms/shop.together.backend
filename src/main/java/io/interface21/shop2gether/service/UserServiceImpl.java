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

import java.util.Optional;

import io.interface21.shop2gether.UserService;
import io.interface21.shop2gether.UserVO;
import org.ameba.annotation.TxService;
import org.ameba.mapping.BeanMapper;

/**
 * A UserServiceImpl is a transactional Spring managed service that deals with {@link UserVO UserVO} instances.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class UserServiceImpl implements UserService {

    private final BeanMapper mapper;
    private final UserRepository userRepository;

    UserServiceImpl(BeanMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<UserVO> getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() ? Optional.of(mapper.map(user.get(), UserVO.class)) : Optional.empty();
    }
}
