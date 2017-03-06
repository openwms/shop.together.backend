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

import io.interface21.shop2gether.service.TextNote;
import org.ameba.mapping.BeanMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * A ItemControllerDocumentation.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class ItemControllerDocumentation extends DocumentationBase {

    private static final String PHONENUMBER = "0815";
    @Autowired
    private BeanMapper mapper;

    public final
    @Test
    void should_Complain_When_Get_Missing_Item() throws Exception {
        super.mockMvc.perform(get(ItemController.RESOURCE_PLURAL +
                "/UNKNOWN_PERSISTENT_KEY"))
                .andExpect(status().isNotFound())
                .andDo(document("31-item-complain-nonexisting"));
    }

    public final
    @Test
    void should_Load_All_Items_For_Owner() throws Exception {
        // setup ...
        TextNote saved = accessor.getItemRepository().save(new TextNote("Title",
                "Some " +
                "awesome text",
                "#CECECE", true));

        // test ...
        MvcResult result = super.mockMvc.perform(get(ItemController.RESOURCE_PLURAL +
                "/" + saved.getpKey()))
                .andExpect(status().isOk())
                .andDo(document("32-item-load-all-for-owner"))
                .andReturn();

        // verify ...
        TextNote returned = objectMapper.readValue(result.getResponse()
                        .getContentAsString(), TextNote.class);
        assertThat(returned)
                .extracting("title", "text", "color", "pinned")
                .contains("Title", "awesome text", "#CECECE", true);

    }
}
