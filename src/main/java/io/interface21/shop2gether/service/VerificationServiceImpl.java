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

import io.interface21.shop2gether.OwnerVO;
import io.interface21.shop2gether.VerificationService;
import io.interface21.shop2gether.VerificationVO;
import org.ameba.annotation.TxService;
import org.ameba.mapping.BeanMapper;

import java.util.Optional;

/**
 * A VerificationServiceImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class VerificationServiceImpl implements VerificationService {

    private final BeanMapper mapper;
    private final Repositories.UserRepository userRepository;
    private final CodeGenerator codeGenerator;

    VerificationServiceImpl(BeanMapper mapper, Repositories.UserRepository userRepository, CodeGenerator codeGenerator) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public VerificationVO request(String phonenumber) {
        VerificationVO verification = new VerificationVO(codeGenerator.generate(), phonenumber);
        Optional<User> optUser = userRepository.findByPhonenumber(phonenumber);
        if (optUser.isPresent()) {
            optUser.get().setVerification(verification);
        } else {
            createUser(phonenumber, verification);
        }
        return verification;
    }

    private void createUser(String phonenumber, VerificationVO verification) {
        User user = new User(phonenumber);
        user.setPhonenumber(phonenumber);
        user.setVerification(verification);
        userRepository.save(user);
    }

    @Override
    public OwnerVO verify(VerificationVO verification) {
        return null;
    }
}
