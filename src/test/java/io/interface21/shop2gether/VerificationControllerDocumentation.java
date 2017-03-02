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

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSender;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * A VerificationControllerDocumentation is a full integration test without slices and
 * mocks.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class VerificationControllerDocumentation extends DocumentationBase {

    @MockBean
    private MailSender mailSender;
    private final OwnerService ownerService;
    private static final String PHONENUMBER = "0815";

    public VerificationControllerDocumentation(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    public final
    @Test
    void shouldCreateNewUser() throws Exception {
        super.mockMvc.perform(get(VerificationController.RESOURCE_PLURAL + "/" +
                PHONENUMBER
        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("code", notNullValue()))
                .andExpect(jsonPath("phonenumber", is(PHONENUMBER)))
                .andDo(document("verification-getfor-phonenumber"));

        ownerService.findAll().get(0);
    }
}
