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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * A VerificationServiceImpl is a transactional Spring managed Service that acts as the
 * entry point into the domain.
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

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<VerificationVO> request(String phonenumber) {
        ResponseEntity<VerificationVO> result;
        VerificationVO verification = VerificationVO.of(codeGenerator.generate(), phonenumber);
        Optional<Owner> optUser = ownerRepository.findByPhonenumber(phonenumber);
        if (optUser.isPresent()) {
            optUser.get().verificationSent(verification);
            result = new ResponseEntity<>(verification, HttpStatus.OK);
        } else {
            createUser(phonenumber, verification);
            result = new ResponseEntity<>(verification, HttpStatus.CREATED);
        }
        sender.send(verification.getCode(), verification.getPhonenumber());
        return result;
    }

    private void createUser(String phonenumber, VerificationVO verification) {
        Owner user = Owner.newBuilder()
                .withUsername(phonenumber)
                .withPhonenumber(phonenumber)
                .withVerificationCode(verification.getCode())
                .build();
        ownerRepository.save(user);
        LOGGER.debug("User with phonenumber [{}] created", phonenumber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OwnerVO verify(VerificationVO v) {
        return mapper.map(
                ownerRepository.findByPhonenumber(v.getPhonenumber())
                        .orElseThrow(NotFoundException::new)
                        .throwIfInvalid(v),
                OwnerVO.class);
    }
}
