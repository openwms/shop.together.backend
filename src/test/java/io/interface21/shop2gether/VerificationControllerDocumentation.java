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

import com.fasterxml.jackson.databind.ObjectMapper;
import io.interface21.shop2gether.service.Owner;
import io.interface21.shop2gether.service.RepoAccessor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSender;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static java.lang.String.valueOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
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
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OwnerService<?> ownerService;
    @Autowired
    private RepoAccessor accessor;
    private static final String PHONENUMBER = "08123";

    public final
    @Test
    void should_Signup_And_Create_New_User() throws Exception {
        List<Owner> owners = accessor.getOwnerRepository().findAll();
        super.mockMvc.perform(get(VerificationController.RESOURCE_PLURAL + "/" +
                PHONENUMBER
        ))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("code", notNullValue()))
                .andExpect(jsonPath("phonenumber", is(PHONENUMBER)))
                .andDo(document("verification-getfor-phonenumber"));

        OwnerVO<?> owner = ownerService.findAll().get(0);
        assertThat(owner.getPhonenumber()).isEqualTo(PHONENUMBER);
        assertThat(owner.getUsername()).isEqualTo(PHONENUMBER);
        assertThat(owner.isNew()).isFalse();
    }

    public final
    @Test
    void should_Signup_And_Not_Create_New_User() throws Exception {
    }

    public final
    @Test
    void should_Verify_And_Return_Owner() throws Exception {
        Owner o = accessor.getOwnerRepository().save(Owner.newBuilder().withUsername
                (PHONENUMBER).withPhonenumber(PHONENUMBER).withVerificationCode("12345")
                .build());
        MvcResult result = super.mockMvc.perform(
                post(VerificationController.RESOURCE_PLURAL)
                        .contentType
                                (APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VerificationVO.of
                                (valueOf(12345), PHONENUMBER))))
                .andExpect(status().isOk())
                .andReturn();
        UserVO userVO = objectMapper.readValue(result.getResponse().getContentAsString(), UserVO.class);
        assertThat(userVO.isNew()).isFalse();

    }
}
