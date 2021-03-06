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

import io.interface21.shop2gether.service.Owner;
import io.interface21.shop2gether.service.TextNote;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A OwnerControllerDocumentation is a full integration test without slices and
 * mocks to test the interaction model with Owners and Users resources.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class OwnerControllerDocumentation extends DocumentationBase {

    private static final String PHONENUMBER = "0815";

    public final
    @Test
    void should_Complain_Nonexisting_Owner() throws Exception {
        super.mockMvc.perform(get(OwnerController.RESOURCE_PLURAL +
                "/UNKNOWN_PERSISTENT_KEY"))
                .andExpect(status().isNotFound())
                .andDo(document("21-owner-complain-nonexisting"));
    }

    public final
    @Test
    void should_Get_Existing_Owner() throws Exception {
        // setup ...
        Owner owner = accessor.getOwnerRepository().save(Owner.newBuilder().withUsername(PHONENUMBER)
                .withUsername(PHONENUMBER).build());
        owner.addItem(new TextNote("Title", "Text", "#CECECE", true));

        // test ...
        MvcResult mvcResult = super.mockMvc.perform(get(OwnerController.RESOURCE_PLURAL +
                "/" + owner.getPersistentKey()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username", is(owner.getUsername())))
                .andExpect(jsonPath("persistentKey", is(owner.getPersistentKey())))
                .andExpect(jsonPath("new", is(false)))
                .andExpect(jsonPath("$.links[0].rel", is("_items")))
                .andExpect(jsonPath("$.links[?(@.rel == 'self')]", notNullValue()))
                .andDo(document("22-owner-get-existing"))
                .andReturn();


    }
}
