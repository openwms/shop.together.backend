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

import org.springframework.hateoas.UriTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * A VerificationController is the HTTP entry point into the domain model by providing
 * signup and verification.
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
    ResponseEntity<VerificationVO> requestCodeFor(@PathVariable @Min(1) String phonenumber) {
        return service.request(phonenumber);
    }

    @PostMapping(RESOURCE_PLURAL)
    void verify(@RequestBody @NotNull VerificationVO verification, HttpServletResponse
            resp) {
        UserVO userVO = service.verify(verification);
        userVO.add(linkTo(methodOn(OwnerController.class).getOwnerFor(userVO
                .getPersistentKey
                        ())).withRel("_self"));
        resp.addHeader(HttpHeaders.LOCATION, userVO.getLink("_self").getHref());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<String> handleIllegalClientRequest(IllegalArgumentException iae) {
        return new ResponseEntity<>(iae.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private String getLocationHeader(HttpServletRequest req, String identifier) {
        return new UriTemplate(req.getRequestURL().append("/{objId}").toString())
                .expand(identifier).toASCIIString();
    }

}
