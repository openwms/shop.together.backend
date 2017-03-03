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
package io.interface21.shop2gether;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * A VerificationController is the entry point into the model does the user
 * verification the first time.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RestController
class VerificationController {

    static final String RESOURCE_PLURAL = "/verifications";
    private final VerificationService service;

    VerificationController(VerificationService service) {
        this.service = service;
    }

    @GetMapping(RESOURCE_PLURAL + "/{phonenumber}")
    ResponseEntity<VerificationVO> requestCodeFor(@PathVariable @Min(1) String
                                                          phonenumber) {
        return service.request(phonenumber);
    }

    @PostMapping(RESOURCE_PLURAL)
    UserVO verify(@RequestBody @NotNull VerificationVO verification) {
        return service.verify(verification);
    }
}
