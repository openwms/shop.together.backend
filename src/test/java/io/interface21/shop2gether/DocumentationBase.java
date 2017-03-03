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
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

/**
 * A DocumentationBase.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RunWith(SpringRunner.class)
@Transactional
@Rollback
@SpringBootTest
public class DocumentationBase {

    protected static final Logger LOGGER = LoggerFactory.getLogger("DOCUMENTATION");
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");
    @Autowired
    protected WebApplicationContext context;

    void setupInternal() throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8", true);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .addFilters(filter)
                .build();
    }

    void teardownInternal() throws Exception {
    }

    @Before
    public final void setUp() throws Exception {
        this.setupInternal();
        onSetup();
    }

    @After
    public final void teardown() throws Exception {
        onTeardown();
        this.teardownInternal();
    }

    protected void onTeardown() {

    }

    protected void onSetup() {

    }
}
