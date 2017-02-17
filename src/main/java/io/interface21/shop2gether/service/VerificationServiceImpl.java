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
import org.ameba.exception.NotFoundException;
import org.ameba.mapping.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * A VerificationServiceImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class VerificationServiceImpl implements VerificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationServiceImpl.class);
    private final BeanMapper mapper;
    private final Repositories.OwnerRepository ownerRepository;
    private final CodeGenerator codeGenerator;
    private final VerificationSender sender;

    VerificationServiceImpl(BeanMapper mapper, Repositories.OwnerRepository ownerRepository, CodeGenerator codeGenerator, VerificationSender sender) {
        this.mapper = mapper;
        this.ownerRepository = ownerRepository;
        this.codeGenerator = codeGenerator;
        this.sender = sender;
    }

    @Override
    public VerificationVO request(String phonenumber) {
        VerificationVO verification = new VerificationVO(codeGenerator.generate(), phonenumber);
        Optional<Owner> optUser = ownerRepository.findByPhonenumber(phonenumber);
        if (optUser.isPresent()) {
            optUser.get().setVerification(verification);
        } else {
            LOGGER.debug("New user with phonenumber [{}] needs to be created", phonenumber);
            createUser(phonenumber, verification);
        }
        sender.send(verification.getCode(), verification.getPhonenumber());
        return verification;
    }

    private void createUser(String phonenumber, VerificationVO verification) {
        Owner user = new Owner(phonenumber);
        user.setPhonenumber(phonenumber);
        user.setVerification(verification);
        ownerRepository.save(user);
    }

    @Override
    public OwnerVO verify(VerificationVO verification) {
        Owner user = ownerRepository.findByPhonenumber(verification.getPhonenumber()).orElseThrow(NotFoundException::new);
        user.throwIfInvalid(verification);
        return mapper.map(user, OwnerVO.class);
    }
}
